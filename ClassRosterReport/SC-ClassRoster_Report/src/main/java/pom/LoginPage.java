package pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* reading the xpath from the property file*/
public class LoginPage {
    private String os = null;
    String dir = null;
    Properties properties = new Properties();
    InputStream input = null;

    public LoginPage() {
        try {
            dir = System.getProperty("user.dir");
            dir = dir.replace("SC-ClassRoster_Report", "");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\Properties\\LoginPage.properties");
            } else {
                input = new FileInputStream(dir + "/Properties/LoginPage.properties");
            }
            properties.load(input);
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

    public String getPassword() {
        return properties.getProperty("LOGIN_PASSWORD_INPUTBOX");
    }

    public String getLoginUserName() {
        return properties.getProperty("LOGIN_USERNAME_INPUTBOX");
    }

    public String getLoginSignIn() {
        return properties.getProperty("LOGIN_SIGNIN_BUTTON");
    }
}
