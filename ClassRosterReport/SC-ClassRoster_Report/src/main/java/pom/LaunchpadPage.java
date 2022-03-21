package pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LaunchpadPage {
    private String os = null;
    String dir = null;
    Properties properties = new Properties();
    InputStream input = null;

    public LaunchpadPage() {
        try {
            dir = System.getProperty("user.dir");
            dir = dir.replace("SC-ClassRoster_Report", "");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\Properties\\LaunchPadPage.properties");
            } else {
                input = new FileInputStream(dir + "/Properties/LaunchPadPage.properties");
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

    public String getLaunchPadMenu() {
        return properties.getProperty("LAUNCHPAD_MENU");
    }

    public String getReport() {
        return properties.getProperty("REPORT_MODULE");
    }
}
