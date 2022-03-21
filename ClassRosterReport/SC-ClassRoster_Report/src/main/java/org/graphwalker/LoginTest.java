package org.graphwalker;

import controllers.LoginController;
import helpers.BrowserInitHelper;
import helpers.DriverHelper;
import utils.*;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;

@SuppressWarnings({"squid:S2696"})
@GraphWalker(value = "random(edge_coverage(100))", start = "e_EnterBaseURL")
public class LoginTest extends ExecutionContext implements org.graphwalker.Login {

    //Declarations
    private String url;
    private String username;
    private String password;
    public static boolean dependant = true;
    public static int passedCount = 0;
    public static int failedCount = 0;
    public static int skippedCount = 0;

    @BeforeExecution
    public void setup() {
        Dynamic.readDynamicProperties();
        Secure.readSecureProperties();
        Static.readStaticProperties();
        BrowserInitHelper.deleteFile(DriverHelper.getDeletePath());
        url = Dynamic.getURL();
        username = Secure.getUsername();
        password = Secure.getPassword();
        BrowserInitHelper.setup();
        LoggerUtility.testStartedBy();
        ConsoleLogger.test = ConsoleLogger.report.startTest(Dynamic.getInstance()+" | Custom Report");
    }

    @AfterExecution
    public void cleanup() {
        if (BrowserInitHelper.getInstance() != null) {
            LoggerUtility.executionEndTime = DriverHelper.getCurrentTime();
            ConsoleLogger.report.endTest(ConsoleLogger.test);
            ConsoleLogger.report.flush();
            LoggerUtility.writeLog();
            LoggerUtility.testEndedBy();
            //BrowserInitHelper.tearDown();
        }
    }

    @Override
    public void e_EnterBaseURL() {
        if (dependant) {
            BrowserInitHelper.getInstance().manage().window().maximize();
            LoggerUtility.executionStartTime = DriverHelper.getCurrentTime();
            BrowserInitHelper.getInstance().get(url);
        }

    }

    /*Verification of URL*/
    public void v_BaseURL() {
        //No Functionality
    }

    /*Login with Valid Credentials*/
    public void e_ValidCredentials() {
        if (dependant)
            LoginController.login(username, password);
    }

    /*Verifying launchpad page*/
    public void v_VerifyLaunchPad() {
        //No Functionality
    }


}
