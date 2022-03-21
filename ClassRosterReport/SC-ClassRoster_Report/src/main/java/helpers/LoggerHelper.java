package helpers;

import utils.ConsoleLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Helper Class for LoggerHelper
 **/
public class LoggerHelper {

    private static final String dir = System.getProperty("user.dir");
    private static final LocalDateTime now = LocalDateTime.now();
    private static PrintWriter writer;
    private static int screenshotCounter = 0;

    public static void logger(WebDriver driver) {
        if (BrowserInitHelper.loggingEnabled) {
            LogEntries logs = driver.manage().logs().get("browser");
            logs.getAll();
            if (logs.toString().length() > 0 && !logs.iterator().toString().contains("openqa") && !logs.iterator().toString().contains("java")) {
                try {
                    screenshotJavascriptErrors("js_error_" + screenshotCounter + ".png", driver);
                    logWriter(logs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logWriter(LogEntries entries) throws FileNotFoundException, UnsupportedEncodingException {
        String filePath = dir + "/target/javascript-logs/" + now + "_js_log.txt";
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            writer.append("==> ").append(entries.iterator().toString()).append("\n");
            writer.close();
        } else {
            writer = new PrintWriter(dir + "/target/javascript-logs/" + now + "_js_log.txt", "UTF-8");
            writer.append("==> ").append(entries.iterator().toString()).append("\n");
        }
        ConsoleLogger.ErrorLog(entries.iterator().toString());
    }

    public static void screenshotJavascriptErrors(String fileName, WebDriver driver) throws IOException {
        screenshotCounter++;
        String screenshotPath = "/target/screenshots/";
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(dir + screenshotPath + fileName));
    }

}
