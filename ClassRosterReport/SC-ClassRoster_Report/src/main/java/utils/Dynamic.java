package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utils Class for Dynamic properties
 **/
public class Dynamic {

    private static String urlSuite, studentURLSuite, urlCps, studentURLCps = null;
    private static String urlHisd, studentURLHisd, urlSdhc, studentURLSdhc = null;

    private static String headless = null;
    private static String browser = null;
    private static String waitTime = null;
    private static String updateParent = null;
    private static String instance = null;
    private static String parentPortalUrl = null;
    private static String userLevel = null;
    private static String updateStudent = null;
    private static String dir = null;
    private static String os = null;
    private static InputStream input = null;
    private static String screenshotLogger = null;
    private static String remoteUrl = null;
    private static String docker = null;

    private static Properties properties = new Properties();

    public static void readDynamicProperties() {
        try {
            dir = System.getProperty("user.dir");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\dynamic.properties");
            } else {
                input = new FileInputStream(dir + "/dynamic.properties");
            }
            properties.load(input);
            urlSuite = properties.getProperty("URL_GENERAL");
            studentURLSuite = properties.getProperty("STUDENT_URL_GENERAL");
            urlCps = properties.getProperty("URL_CPS");
            studentURLCps = properties.getProperty("STUDENT_URL_CPS");
            urlHisd = properties.getProperty("URL_HISD");
            studentURLHisd = properties.getProperty("STUDENT_URL_HISD");
            urlSdhc = properties.getProperty("URL_SDHC");
            studentURLSdhc = properties.getProperty("STUDENT_URL_SDHC");
            parentPortalUrl = properties.getProperty("PARENT_PORTAL_URL");
            updateParent = properties.getProperty("UPDATE_PARENT");
            updateStudent = properties.getProperty("UPDATE_STUDENT");
            userLevel = properties.getProperty("USER_LEVEL");
            instance = properties.getProperty("INSTANCE");
            headless = properties.getProperty("HEADLESS");
            browser = properties.getProperty("BROWSER");
            waitTime = properties.getProperty("WAIT_TIME");
            screenshotLogger = properties.getProperty("SCREENSHOTS_LOGGER");
            remoteUrl = properties.getProperty("REMOTEURL");
            docker = properties.getProperty("DOCKER");
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

    public static String getURL() {
        String url = null;
        if (Dynamic.getInstance().equalsIgnoreCase("GENERAL")) {
            url = urlSuite;
        } else if (Dynamic.getInstance().equalsIgnoreCase("CPS")) {
            url = urlCps;
        } else if (Dynamic.getInstance().equalsIgnoreCase("HISD")) {
            url = urlHisd;
        } else if (Dynamic.getInstance().equalsIgnoreCase("SDHC")) {
            url = urlSdhc;
        }
        return url;
    }

    public static String getStudentURL() {
        String studentURL = null;
        if (Dynamic.getInstance().equalsIgnoreCase("GENERAL")) {
            studentURL = studentURLSuite;
        } else if (Dynamic.getInstance().equalsIgnoreCase("CPS")) {
            studentURL = studentURLCps;
        } else if (Dynamic.getInstance().equalsIgnoreCase("HISD")) {
            studentURL = studentURLHisd;
        } else if (Dynamic.getInstance().equalsIgnoreCase("SDHC")) {
            studentURL = studentURLSdhc;
        }
        return studentURL;
    }

    public static String getParentPortalUrl() {
        return parentPortalUrl;
    }

    public static String getUpdateParent() {
        return updateParent;
    }

    public static String getUpdateStudent() {
        return updateStudent;
    }

    public static String getUserLevel() {
        return userLevel;
    }

    public static String getInstance() {
        return instance;
    }

    public static boolean getHeadless() {
        boolean headlessBoolean = Boolean.parseBoolean(headless);
        return headlessBoolean;
    }

    public static String getBrowser() {
        browser.toLowerCase();
        return browser;
    }

    public static Integer getWaitTime() {
        int waitTimeInt = Integer.parseInt(waitTime);
        return waitTimeInt;
    }

    public static String getScreenshotLogger() {
        return screenshotLogger;
    }

    public static String getRemoteUrl() {
        return remoteUrl;
    }

    public static String getDocker() {
        return docker;
    }
}
