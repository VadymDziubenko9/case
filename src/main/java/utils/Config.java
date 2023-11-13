package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@SuppressWarnings("all")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
    public static final String USER_NAME = getUserEmail();
    public static final String USER_PASSWORD = getUserPassword();
    public static final String BASE_URL = getBaseUrl();

    private static String getUserEmail() {
        var email = StringUtils.isEmpty(System.getenv("userEmail")) ? System.getProperty("userEmail") : System.getenv("userEmail");
        if (StringUtils.isEmpty(email)) throw new IllegalArgumentException("Email is not provided!");
        return email;
    }

    private static String getUserPassword() {
        var password = StringUtils.isEmpty(System.getenv("userPassword")) ? System.getProperty("userPassword") : System.getenv("userPassword");
        if (StringUtils.isEmpty(password)) throw new IllegalArgumentException("Password is not provided!");
        return password;
    }

    private static String getBaseUrl() {
        var pageUrl = StringUtils.isEmpty(System.getenv("baseUrl")) ? System.getProperty("baseUrl") : System.getenv("baseUrl");
        if (StringUtils.isEmpty(pageUrl)) throw new IllegalArgumentException("Base Url is not provided!");
        return pageUrl;
    }


}
