package controllers;

import helpers.BrowserInitHelper;
import helpers.DriverHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pom.LoginPage;
import utils.ConsoleLogger;

@SuppressWarnings({"squid:S1118"})
public class LoginController {

    private static LoginPage loginPage=new LoginPage();

    /*Method to enter Password*/
    public static void setPassword(String xpath, String text) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) BrowserInitHelper.getInstance();
            executor.executeScript("document.getElementById('" + xpath + "').value='" + text + "'");
        } catch (Exception e) {
            ConsoleLogger.DebugLog("Password is not entered.");
        }
    }

    /* Login with valid Username and Password  */
    public static void login(String username, String password) {
        try {
            BrowserInitHelper.getMaxWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(loginPage.getLoginUserName())));
            DriverHelper.sendKeysXpath(loginPage.getLoginUserName(), username);
            setPassword(loginPage.getPassword(), password);
            /*click on Sign In button*/
            DriverHelper.clickXpath(loginPage.getLoginSignIn());
            DriverHelper.waitUntilLoaderInvisible();
        } catch (Exception e) {
            ConsoleLogger.DebugLog("Exception Handled for login method");
        }
    }
}
