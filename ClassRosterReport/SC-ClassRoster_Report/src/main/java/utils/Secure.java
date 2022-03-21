package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utils Class for Secure properties
 **/
public class Secure {

    private static String username = null;
    private static String password = null;
    private static String studentUsername = null;
    private static String studentPassword = null;
    private static String dir = null;
    private static String os = null;
    private static InputStream input = null;

    private static Map<String, String> map = new HashMap<String, String>();

    private static Properties properties = new Properties();

    public static void readSecureProperties() {
        try {
            dir = System.getProperty("user.dir");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\secure.properties");
            } else {
                input = new FileInputStream(dir + "/secure.properties");
            }
            properties.load(input);
            username = properties.getProperty("USERNAME");
            password = properties.getProperty("PASSWORD");
            studentUsername = properties.getProperty("STUDENTUSERNAME");
            studentPassword = properties.getProperty("STUDENTPASSWORD");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getStudentUsername() {
        return studentUsername;
    }

    public static String getStudentPassword() {
        return studentPassword;
    }
}
