package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utils Class for Static properties
 **/
public class Static {

    private static String closeBrowser = null;
    private static String screenshots = null;
    private static String chrome = null;
    private static String firefox = null;
    private static String dir = null;
    private static String os = null;
    private static InputStream input = null;
    private static String productVersionGeneral, productVersionSdhc, productVersionCps, productVersionHisd = null;
    private static String districtLevelProjectTotalTestCases, testCasesCps, schoolLevelProjectTotalTestCases, teacherLevelProjectTotalTestCases, networkLevelProjectTotalTestCases = null;
    private static String projectName = null;

    private static Properties properties = new Properties();

    public static void readStaticProperties() {
        try {
            dir = System.getProperty("user.dir");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\static.properties");
            } else {
                input = new FileInputStream(dir + "/static.properties");
            }
            properties.load(input);
            productVersionGeneral = properties.getProperty("PRODUCT_VERSION_GENERAL");
            productVersionCps = properties.getProperty("PRODUCT_VERSION_CPS");
            productVersionHisd = properties.getProperty("PRODUCT_VERSION_HISD");
            productVersionSdhc = properties.getProperty("PRODUCT_VERSION_SDHC");
            projectName = properties.getProperty("PROJECT_NAME");
            districtLevelProjectTotalTestCases = properties.getProperty("CLASSROSTER_REPORT_DISTRICT_TC");
            closeBrowser = properties.getProperty("CLOSE_BROWSER");
            screenshots = properties.getProperty("SCREENSHOTS");
            chrome = properties.getProperty("CHROME");
            firefox = properties.getProperty("FIREFOX");
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

    public static String getCloseBrowser() {
        closeBrowser.toLowerCase();
        return closeBrowser;
    }

    public static String getScreenshots() {
        screenshots.toLowerCase();
        return screenshots;
    }

    public static String getChrome() {
        chrome = chrome.toLowerCase();
        return chrome;
    }

    public static String getFirefox() {
        return firefox;
    }

    public static String getProductVersion() {
        String productVersion = null;
        if (Dynamic.getInstance().equalsIgnoreCase("GENERAL")) {
            productVersion = productVersionGeneral;
        } else if (Dynamic.getInstance().equalsIgnoreCase("CPS")) {
            productVersion = productVersionCps;
        } else if (Dynamic.getInstance().equalsIgnoreCase("HISD")) {
            productVersion = productVersionHisd;
        } else if (Dynamic.getInstance().equalsIgnoreCase("SDHC")) {
            productVersion = productVersionSdhc;
        }
        return productVersion;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static float getProjectTotalTestCases() {
        return Float.parseFloat(districtLevelProjectTotalTestCases);
    }
}