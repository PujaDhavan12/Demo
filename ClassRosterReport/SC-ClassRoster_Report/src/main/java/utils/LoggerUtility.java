package utils;

import helpers.BrowserInitHelper;
import helpers.DriverHelper;

import java.io.*;
import java.util.concurrent.TimeUnit;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.graphwalker.LoginTest.*;


/**** Utils class for LoggerUtility *****/
public class LoggerUtility {

    //Product Version
    private final static String productVersion = Static.getProductVersion();
    //Total Test Cases
    public static float totalTC = Static.getProjectTotalTestCases();
    //Project Name
    public static String projectName = Static.getProjectName();
    public static float executedTC;
    public static String executionStartTime = "NotStarted";
    public static String executionEndTime = "NA";
    public static int executionTime;
    private static String dir = null;
    private static String os = null;
    private static InputStream input = null;
    private static File outputLog = null;
    private static float executedTCPercent;
    private static float successCountPercent;
    private static float failureCountPercent;
    private static float skippedCountPercent;
    private static String executedTC_Percent;
    private static String successCount_Percent;
    private static String failureCount_Percent;
    private static String skippedCount_Percent;
    private static String hostname = "Unknown";
    private static String userName = "Unknown";

    public static void testStartedBy() {
        File beforeLog = null;
        os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            beforeLog = new File(System.getProperty("user.dir") + "\\log\\BeforeReadLog.txt");
        } else {
            beforeLog = new File(System.getProperty("user.dir") + "/log/BeforeReadLog.txt");
        }

        File file = new File(String.valueOf(beforeLog));
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter writer = new PrintWriter(fileWriter);

        writer.println(projectName + "Test Started By : " + getUserName());
        writer.println("PLEASE DO NOT RUN UNTIL THE TEST IS FINISHED");
        writer.close();

        System.out.println(projectName + "Test Started By : " + getUserName());
        System.out.println("PLEASE DO NOT RUN UNTIL THE TEST IS FINISHED");
    }

    public static void testEndedBy() {
        File afterLog = null;
        os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            afterLog = new File(System.getProperty("user.dir") + "\\log\\AfterReadLog.txt");
        } else {
            afterLog = new File(System.getProperty("user.dir") + "/log/AfterReadLog.txt");
        }

        File file = new File(String.valueOf(afterLog));
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter writer = new PrintWriter(fileWriter);
        writer.println(projectName + "Test Ended By : " + getUserName());
        writer.println("TEST IS FINISHED NOW");
        writer.close();
    }

    public static String getHostName() {
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();

        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }
        return hostname;
    }

    public static String getUserName() {
        userName = System.getProperty("user.name");
        return userName;
    }

    //Droplet test setup report
    public static void writeLog() {
        executionTime = (int) BrowserInitHelper.stopwatch.elapsed(TimeUnit.SECONDS);
        executedTC = passedCount + failedCount;
        executedTCPercent = (executedTC / totalTC) * 100;
        successCountPercent = (passedCount / totalTC) * 100;
        failureCountPercent = (failedCount / totalTC) * 100;
        skippedCountPercent = (skippedCount / totalTC) * 100;
        executedTC_Percent = DriverHelper.roundOffTo2DecPlaces(executedTCPercent);
        successCount_Percent = DriverHelper.roundOffTo2DecPlaces(successCountPercent);
        failureCount_Percent = DriverHelper.roundOffTo2DecPlaces(failureCountPercent);
        skippedCount_Percent = DriverHelper.roundOffTo2DecPlaces(skippedCountPercent);

        os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            outputLog = new File(System.getProperty("user.dir") + "\\log\\readLog.txt");
        } else {
            outputLog = new File(System.getProperty("user.dir") + "/log/readLog.txt");
        }
        File file = new File(String.valueOf(outputLog));
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter writer = new PrintWriter(fileWriter);
        writer.println("==================================================");
        writer.println("Host Name            : " + getHostName());
        writer.println("User Name            : " + getUserName());
        writer.println("Product Name         : " + Dynamic.getInstance());
        writer.println("Product Version      : " + productVersion);
        writer.println("Date                 : " + DriverHelper.getCurrentDate());
        writer.println("Project Name         : " + projectName);
        writer.println("Site Name            : " + Dynamic.getURL());
        writer.println("User Level           : " + Dynamic.getUserLevel());
        writer.println("Total TestCases      : " + (int) totalTC);
        writer.println("Executed TestCases   : " + (int) executedTC + "(" + executedTC_Percent + "%)");
        writer.println("Passed TestCases     : " + passedCount + "(" + successCount_Percent + "%)");
        writer.println("Failed TestCases     : " + failedCount + "(" + failureCount_Percent + "%)");
        writer.println("Skipped TestCases    : " + (int) skippedCount + "(" + failureCount_Percent + "%)");
        writer.println("Execution Start Time : " + executionStartTime);
        writer.println("Execution End Time   : " + executionEndTime);
        writer.println("Execution time       : " + DriverHelper.getDurationString(executionTime));
        writer.println("==================================================");
        writer.close();

        //Print in Console
        System.out.println("===========================================================");
        System.out.println("Host Name            : " + hostname);
        System.out.println("User Name            : " + userName);
        System.out.println("Product Name         : " + Dynamic.getInstance());
        System.out.println("Product Version      : " + productVersion);
        System.out.println("Date                 : " + DriverHelper.getCurrentDate());
        System.out.println("Project Name         : " + projectName);
        System.out.println("Site Name            : " + Dynamic.getURL());
        System.out.println("User Level           : " + Dynamic.getUserLevel());
        System.out.println("Total TestCases      : " + (int) totalTC);
        System.out.println("Executed TestCases   : " + (int) executedTC + "(" + executedTC_Percent + "%)");
        System.out.println("Passed TestCases     : " + passedCount + "(" + successCount_Percent + "%)");
        System.out.println("Failed TestCases     : " + failedCount + "(" + failureCount_Percent + "%)");
        System.out.println("Skipped TestCases    : " + (int) skippedCount + "(" + skippedCount_Percent + "%)");
        System.out.println("Execution Start Time : " + executionStartTime);
        System.out.println("Execution End Time   : " + executionEndTime);
        System.out.println("Execution time       : " + DriverHelper.getDurationString(executionTime));
        System.out.println("===========================================================");

    }
}
