package advanced.md.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Patient {
    private String patientName;
    private String chartNumber;
    private String aptSuite;
    private String responsiblePartyName;
    private String address;
    private String zipCode;
    private String city;
    private String state;
    private String email;
    private String notes;
    private String preferredPhone;
    private String dateOfBirth;
    private String financialClass;
    private String providerProfile;
    private String preferredLanguage;
    private List<String> memos;
    private List<String> additionalNotes;
    private String sex;
}
