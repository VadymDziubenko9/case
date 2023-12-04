package advancedMD;

import advanced.md.HomePageAdvancedMd;
import advanced.md.LoginPageAdvancedMd;
import advanced.md.PatientPageAdvancedMd;
import advanced.md.dto.Patient;
import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.WebDriverUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static utils.JsUtil.waitForDomToLoad;


@Slf4j
public class PatientDataParsingTest {
    private static int LAST_PROCESSED_ROW_INDEX = 0;
    private final LoginPageAdvancedMd loginPage = new LoginPageAdvancedMd();
    private final HomePageAdvancedMd homePageMD = new HomePageAdvancedMd();

    private static String getConcatenatedValue(Cell cell1, Cell cell2, Cell cell3) {
        String value1 = (cell1 != null) ? cell1.toString() : "";
        String value2 = (cell2 != null) ? cell2.toString() : "";
        String value3 = (cell3 != null) ? cell3.toString() : "";

        value1 = (value1 != null && !value1.isEmpty()) ? capitalizeFirstLetter(value1.toLowerCase()) : "";
        value2 = (value2 != null && !value2.isEmpty()) ? capitalizeFirstLetter(value2.toLowerCase()) : "";
        value3 = (value3 != null && !value3.isEmpty()) ? capitalizeFirstLetter(value3.toLowerCase()) : "";

        if (!value3.isEmpty()) {
            if (!value1.isEmpty() && !value2.isEmpty()) {
                return value1 + "," + value2 + " " + value3;
            }
        } else if (!value1.isEmpty() && !value2.isEmpty()) {
            return value1 + "," + value2;
        }
        return null;
    }

    private static @NonNull String capitalizeFirstLetter(@NonNull String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static HashMap<Integer, String> inputStreamFromExcel() {
        HashMap<Integer, String> concatenatedValues = new HashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream("/Users/vadymdziubenko/Desktop/AdvancedMD Patient Listing.xlsx")) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int maxRows = 14456;

            if (LAST_PROCESSED_ROW_INDEX >= maxRows) {
                return null;
            }

            for (int rowIndex = LAST_PROCESSED_ROW_INDEX + 1; rowIndex <= maxRows; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell1 = row.getCell(1);
                    Cell cell3 = row.getCell(2);
                    Cell cell4 = row.getCell(3);
                    Cell cell5 = row.getCell(4);

                    if (!(cell1.toString().isEmpty() && cell3.toString().isEmpty())) {
                        LAST_PROCESSED_ROW_INDEX = rowIndex;
                        concatenatedValues.put((int) Double.parseDouble(String.valueOf(cell1)), getConcatenatedValue(cell3, cell4, cell5));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return concatenatedValues;
    }

    private static void outputJsonStream(String outputDirectory, Patient patient) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(patient);

            File folder = new File(outputDirectory);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = outputDirectory + "/%s.json".formatted(patient.getPatientName());

            try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                fileWriter.write(json + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setupConfig() {
        WebDriverUtil.initDriver();
        login();
    }

    @Test
    public void exportPatientInfoIntoExel() {
//        jsonIssue();

        HashMap<Integer, String> concatenatedValues = inputStreamFromExcel();
        assert concatenatedValues != null;
        var startIndex = 5791;
        var retryCount = 3;
        List<Integer> keysInRange = concatenatedValues.keySet().stream()
                .filter(key -> key >= startIndex)
                .toList();

        for (Integer key : keysInRange) {
            String concatenatedValue = concatenatedValues.get(key);

            if (!homePageMD.openPatientTab().searchAndOpenPatientWithRetry(concatenatedValue, retryCount)) {
                log.info("{} not found. Continuing with the next value...", concatenatedValue);
                homePageMD.closeAllTabs();
                continue;
            }
            String outputDirectory = "advancedMD/" + key;

            var parsedPatientData = new PatientPageAdvancedMd().parsePatientData();
            parsedPatientData.setAdditionalNotes(homePageMD.openNotesTab().parsePatientNotes());
            parsedPatientData.setMemos(homePageMD.openMemosTab().parsePatientMemos());
            outputJsonStream(outputDirectory, parsedPatientData);

            var chartFilesId = homePageMD.openChartFilesTab().getChartFilesId();
            downloadFileCharts(outputDirectory, chartFilesId);

            homePageMD.closeAllTabs();
        }
    }

    public static void jsonIssue() {
        try {
            String baseDirectoryPath = "advancedMD";

            for (int folderNumber = 1260; folderNumber <= 1294; folderNumber++) {
                String folderPath = baseDirectoryPath + "/" + folderNumber;

                Files.list(Paths.get(folderPath))
                        .filter(path -> path.toString().endsWith(".json"))
                        .forEach(PatientDataParsingTest::processJsonFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processJsonFile(Path filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = filePath.toFile();
            ObjectNode rootNode = objectMapper.readValue(jsonFile, ObjectNode.class);

            swapHeaders(rootNode);

            objectMapper.writeValue(jsonFile, rootNode);

            System.out.println("JSON file modified successfully: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void swapHeaders(ObjectNode rootNode) {
        JsonNode memosNode = rootNode.get("memos");
        JsonNode additionalNotesNode = rootNode.get("additionalNotes");

        rootNode.set("memos", additionalNotesNode);
        rootNode.set("additionalNotes", memosNode);
    }


    private void login() {
        open("https://login.advancedmd.com/");
        waitForDomToLoad();
        var parent = getWebDriver().getWindowHandle();
        loginPage.login();

        var handles = getWebDriver().getWindowHandles();
        for (String childWindow : handles) {
            if (!parent.equals(childWindow)) {
                getWebDriver().switchTo().window(childWindow);
            }
        }
    }

    public void downloadFileCharts(String outputDirectory, List<String> chartId) {
        String downloadPdfPath = "https://pm-wfe-130.advancedmd.com/practicemanager/clientfiles/display.aspx?&id=%s&ok=142406&fullsize=1";

        if (!chartId.isEmpty()) {
            for (String s : chartId) {
                open(downloadPdfPath.formatted(s));
                sleep(2000);
            }
        }

        try {
            Files.list(Paths.get(Configuration.downloadsFolder))
                    .filter(Files::isDirectory)
                    .forEach(folder -> {
                        try {
                            if (folder.getFileName().toString().length() > 10) {
                                Files.list(folder)
                                        .forEach(file -> {
                                            try {
                                                Files.move(file, Paths.get(outputDirectory, file.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//        if (directory.exists() && directory.isDirectory()) {
//            File[] directories = directory.listFiles(File::isDirectory);
//            assert directories != null;
//            Arrays.sort(directories, Comparator.comparingLong(File::lastModified).reversed());
//            if (directories.length > 0) {
//                File lastModifiedDirectory = directories[0];
//                File newDirectory = new File(directory, outputDirectory);
//                lastModifiedDirectory.renameTo(newDirectory);
//            }
//        }

