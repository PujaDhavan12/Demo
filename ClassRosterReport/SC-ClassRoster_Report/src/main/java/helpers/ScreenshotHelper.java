package helpers;

import utils.ConsoleLogger;
import utils.Static;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Helper class for Screenshot
 **/
public class ScreenshotHelper {

    //Method to get the Screenshot
    public static void screenshot(WebDriver driver) {
        try {
            String dir = null;
            dir = System.getProperty("user.dir");
            int screenshotCounter = 1;
            if (Static.getScreenshots().equals("true")) {
                String fileName = "screenshot_" + Integer.toString(screenshotCounter) + ".png";
                screenshotCounter++;
                String screenshotPath = "/target/screenshots/";
                File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(file, new File(dir + screenshotPath + fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To delete the Screenshot before execution
    public static String getDeleteScreenShot() {
        String os = null;
        String dir = System.getProperty("user.dir");
        dir = dir.replace("SC-CPS_Help", "");
        String deletePath = null;

        os = System.getProperty("os.name");
        try {
            if (os.contains("Windows")) {
                deletePath = dir + "\\dist\\Screenshots";

            } else {
                deletePath = dir + "/dist/Screenshots";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception handled for method - DeleteScreenShot...");
        }
        ConsoleLogger.DebugLog("deletePath ==>" + deletePath);
        return deletePath;
    }

    //To take the Screenshot
    public static void takeScreenshot(String filename, String elementName) {
        try {
            String path = System.getProperty("user.dir");
            String screenshotPath = path + "\\..\\dist\\Screenshots\\";
            File src = ((TakesScreenshot) BrowserInitHelper.getInstance()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath + filename));
            ConsoleLogger.SuccessLog("" + elementName + " is captured.....");
        } catch (Exception e) {
            System.out.println("Exception handled for " + e);
            ConsoleLogger.FailedTestCase("" + elementName + " is NOT captured.....!!!");
        }
    }

    public static String capture(WebDriver driver){
        String errflpath = null;
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File Dest = new File(System.getProperty("user.dir") + "\\log\\" + System.currentTimeMillis()
                    + ".png");
            errflpath = Dest.getAbsolutePath();
            FileUtils.copyFile(scrFile, Dest);
            return errflpath;
        }catch (IOException e){
            e.printStackTrace();
        }
        return errflpath;
    }

}
