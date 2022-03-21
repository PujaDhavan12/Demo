package utils;

import helpers.BrowserInitHelper;
import helpers.ScreenshotHelper;
import org.graphwalker.LoginTest;
import org.openqa.selenium.logging.LogEntries;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;

/**
 * Utils class for ConsoleLogger
 **/
public class ConsoleLogger {
    public static File filePath = null;

    public static ExtentReports report = new ExtentReports(String.valueOf(reportPath()), true);

    public static ExtentTest test;

    public static File reportPath() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                filePath = new File(System.getProperty("user.dir") + "\\log\\CustomReport.html");
            } else {
                filePath = new File(System.getProperty("user.dir") + "/log/CustomReport.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static void DebugLog(String str) {
        System.out.println(Colors.Color.YELLOW_BOLD + "[MBT-DEBUGGER] " + str + Colors.Color.RESET);
        //  ScreenshotHelper.capture(BrowserInitHelper.getInstance());
    }

    public static void ErrorLog(String str) {
        System.out.println(Colors.Color.RED_BOLD + "[MBT-ERROR] " + str + Colors.Color.RESET);
        BrowserInitHelper.tearDown();
        System.exit(0);
    }

    public static void FailedTestCase(String str) {
        if (Dynamic.getScreenshotLogger().equals("true")) {
            test.log(LogStatus.FAIL, test.addScreenCapture(ScreenshotHelper.capture(BrowserInitHelper.getInstance())) + str);
        } else {
            test.log(LogStatus.FAIL, str);
        }
        System.out.println(Colors.Color.RED_BOLD + "[MBT-FAILED-TEST-CASE] " + str + Colors.Color.RESET);
    }

    public static void SkippedTestCase(String str) {
        LoginTest.skippedCount++;
        if (Dynamic.getScreenshotLogger().equals("true")) {
            test.log(LogStatus.SKIP, test.addScreenCapture(ScreenshotHelper.capture(BrowserInitHelper.getInstance())) + str);
        } else {
            test.log(LogStatus.SKIP, str);
        }
        System.out.println(Colors.Color.BLUE_BOLD + "[MBT-SKIPPED-TEST-CASE] " + str + Colors.Color.RESET);
    }

    public static void JSErrorLog(LogEntries logs) {
        System.out.println(Colors.Color.RED_BOLD + "[MBT-JS-ERROR] " + logs + Colors.Color.RESET);
    }

    public static void SuccessLog(String str) {
        try {
            test.log(LogStatus.PASS, str);
            System.out.println(Colors.Color.GREEN_BOLD + "[MBT-SUCCESS] " + str + Colors.Color.RESET);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
