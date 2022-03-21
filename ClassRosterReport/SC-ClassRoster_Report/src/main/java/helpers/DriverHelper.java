package helpers;

import org.graphwalker.LoginTest;
import utils.*;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static helpers.JavascriptHelper.highlight;
import static org.graphwalker.LoginTest.dependant;


public class DriverHelper {
    private static String dir = System.getProperty("user.dir");
    private static int screenshotCounter = 0;
    private static LocalDateTime now = LocalDateTime.now();
    private static PrintWriter writer;
    private static WebDriver instance;
    public static Dynamic dynamic = new Dynamic();
    public static Static staticpage = new Static();
    public static Secure secure = new Secure();

    /**
     * LoggerFactory
     */
    private static final Logger log = LoggerFactory.getLogger(DriverHelper.class);

    /**
     * Creates manager  of Chrome
     */
    public static void setup() {
        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
    }

    /**
     * Tear down Chrome Driver
     */
    public static void tearDown() {
        getInstance().quit();
    }

    /**
     * Create an instance of Chrome WebDriver
     */
    private static class WebDriverHolderChrome {
        private static final WebDriver INSTANCEChrome = new ChromeDriver(getChromeOptions());
    }

    private static class WebDriverHolderFirefox {
        private static final WebDriver INSTANCEFirefox = new FirefoxDriver();
    }

    private static class WebDriverHolderEdge {
        private static final WebDriver INSTANCEEdge = new EdgeDriver();
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("--disable-gpu");
        options.addArguments("window-size=2100,1600");
        return options;
    }

   

    public static void waitForPageLoadComplete() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(BrowserInitHelper.getInstance(), 30);
        wait.until(pageLoadCondition);
    }

    public static WebDriver getInstance() {
        Dynamic dynamic = new Dynamic();
        String browserName = dynamic.getBrowser();

        if (browserName.equals("chrome")) {
            instance = WebDriverHolderChrome.INSTANCEChrome;
        } else if (browserName.equals("firefox")) {
            instance = WebDriverHolderFirefox.INSTANCEFirefox;
        } else {
            instance = WebDriverHolderEdge.INSTANCEEdge;
        }

        return instance;
    }

    /**
     * Waiter helper methods
     */
    public void setPassword_JS(String value) {
        JavascriptExecutor js = (JavascriptExecutor) DriverHelper.getInstance();
        js.executeScript("document.getElementsByName('password').item(0).value = '" + value + "';");
    }

    public void clickCSSSelector(String css) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector(css))).click();
    }

    public void clickId(String id) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    public WebElement getElementByXpath(String elementXpath) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
        WebElement element = DriverHelper.getInstance().findElement(By.xpath(elementXpath));
        return element;
    }

    public void clickLink_JS(String link) {
        boolean clicked = false;
        do {
            try {
                WebElement Element = DriverHelper.getInstance().findElement(By.linkText(link));
                ((JavascriptExecutor) DriverHelper.getInstance()).executeScript("arguments[0].click();", Element);
            } catch (WebDriverException e) {
                continue;
            } finally {
                clicked = true;
            }
        } while (!clicked);
    }

    public static void waitUntilElementInvisible_ByXPath(String xpath) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    public void clickXpath_JS(String xpath) {
        boolean clicked = false;
        do {
            try {
                WebElement Element = DriverHelper.getInstance().findElement(By.xpath(xpath));
                ((JavascriptExecutor) DriverHelper.getInstance()).executeScript("arguments[0].click();", Element);
            } catch (WebDriverException e) {
                continue;
            } finally {
                clicked = true;
            }
        } while (!clicked);
    }

    public void clickID_JS(String ID) {
        ((JavascriptExecutor) DriverHelper.getInstance()).executeScript("return !!jQuery(\"#" + ID + "\").click();");
    }

    public void sendKeysCss(String css, String text) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector(css))).clear();
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector(css))).sendKeys(text);
    }

    public void sendKeysId(String id, String text) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.id(id)));
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.id(id))).clear();
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.id(id))).sendKeys(text);
    }

    public static void waitUntil(String xpath) {
       BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static void waitUntilLoaderInvisible() {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='divPageLoading']")));
    }

    public void assertion(String xpath, String expected) {
        Assert.assertEquals(BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().trim(), expected);
    }

    public static String getText(String xpath) {
        String text = BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).getText();
        return text;
    }

    public static String getTextByElement(WebElement element) {
        String text = BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOf(element)).getText().trim();
        return text;
    }

    public static void logger(WebDriver driver) {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        logs.filter(Level.SEVERE);
        if (logs != null && !logs.iterator().toString().contains("openqa") && !logs.iterator().toString().contains("java")) {
            try {
                screenshotJavascriptErrors("js_error_" + Integer.toString(screenshotCounter) + ".png", driver);
                logWriter(logs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logWriter(LogEntries entries) throws FileNotFoundException, UnsupportedEncodingException {
        String filePath = dir + "/target/javascript-logs/" + now + "_js_log.txt";
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            writer.append("==> " + entries.iterator().toString() + "\n");
            writer.close();
        } else {
            writer = new PrintWriter(dir + "/target/javascript-logs/" + now + "_js_log.txt", "UTF-8");
            writer.append("==> " + entries.iterator().toString() + "\n");


        }
    }

    public static void screenshotJavascriptErrors(String fileName, WebDriver driver) throws IOException {
        screenshotCounter++;
        String screenshotPath = "/target/screenshots/";
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(dir + screenshotPath + fileName));
    }

    public Boolean getChecked(String id) {
        Boolean value = false;

        JavascriptExecutor js = (JavascriptExecutor) instance;
        Object Check = js.executeScript("return document.getElementById('" + id + "').checked;");

        if (Check.toString().equalsIgnoreCase("true")) {
            value = true;
        }

        return value;
    }

    public void ClickByID_Javascript(String id) {
        JavascriptExecutor js = (JavascriptExecutor) instance;
        js.executeScript("return document.getElementById('" + id + "').click();");
    }

    public void ClickBy_Javascript(String css_value) {
        JavascriptExecutor js = (JavascriptExecutor) instance;
        js.executeScript("return !!jQuery(\"" + css_value + "\").click();");
    }

    public void MultipleClickByID_Javascript(String value) {
        //split value by comma and add in list
        List<String> SplittedList = Arrays.asList(value.split(","));

        for (int i = 0; i < SplittedList.size(); i++) {
            JavascriptExecutor js = (JavascriptExecutor) instance;
            js.executeScript("return document.getElementById('" + SplittedList.get(i) + "').click();");
        }
    }

    public static void ScrollTillElement_Javascript(String xpath) {
        WebElement element = DriverHelper.getInstance().findElement(By.xpath(xpath));
        JavascriptExecutor js = (JavascriptExecutor) instance;
        js.executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
    }

    public String getClassName(WebElement element) {
        String className = "";
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(element));
        className = element.getAttribute("class").trim();
        return className;
    }

    public String getAdminMethodCheckboxID(String checkboxName) {
        String ID = DriverHelper.getInstance().findElement(By.xpath("//*[normalize-space(.) = '" + checkboxName + "']/parent::label")).getAttribute("id");
        JavascriptExecutor js = (JavascriptExecutor) instance;
        //   System.out.println("ID: " + ID);
        Object Val = js.executeScript("return document.getElementById(\"" + ID + "\").children[1].getAttribute(\"id\");");
        return Val.toString();
    }

    public String getOnlineToolsCheckboxID(String checkboxName) {
        String spanID = DriverHelper.getInstance().findElement(By.xpath("//span[normalize-space(.) = '" + checkboxName + "']")).getAttribute("id");
        JavascriptExecutor js = (JavascriptExecutor) instance;
        //  System.out.println("spanID: " + spanID);
        Object Val = js.executeScript("return document.getElementById(\"" + spanID + "\").nextSibling.getAttribute(\"id\");");
        return Val.toString();
    }

    public static void sendKeysCKEditorXpath(String xpath, String text) {
       // logger(BrowserInitHelper.getInstance());
        highlight(BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))));
        //BrowserInitHelper.BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).clear();
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).sendKeys(text);
    }

    public static boolean elementExistence(WebElement element, WebDriver driver) {
       // logger(driver);
        try {
            if (element.isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean elementExistenceXpath(String xPath) {
        try {
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xPath)).isDisplayed()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPath(String path) {
        String regex = "\\\\";
        String subst = "/";
        String value;
        String os = System.getProperty("os.name");

        if (os.contains("Windows") || os.contains("windows")) {
            value = path;
            // System.out.println("Os is Windows.....");
        } else {
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(path);
            value = matcher.replaceAll(subst);
        }

        // System.out.println("Result ==>" + value);

        return value;
    }

    public static boolean replaceAndVerifyTextByXpath(String xpath, String text) {
        boolean res = false;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getText().contains(text)) {
                res = true;
            } else {
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleLogger.ErrorLog("Specified element text doesn't match with  the actual value.");
        }
        return res;
    }

    public static void WaitUntilLoad(long s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException handled....");
        }
    }

    public static void WaitUntilLoad_Click(long s, String xpath) {
        try {
            Thread.sleep(s);
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).click();
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException handled....");
        } catch (Exception e) {
            System.out.println("Exception handled....");
        }
    }

    public WebElement getElementByID(String elementID) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id(elementID)));
        WebElement element = BrowserInitHelper.getInstance().findElement(By.id(elementID));
        return element;
    }

    public static String getReplacedLocator(String toReplace, String xpath) {
        String value = null;
        try {
            value = xpath;
            value = value.replace("TOREPLACE", toReplace);

        } catch (Exception e) {
            System.out.println("Exception handled for getReplacedLocator");
        }
        return value;
    }

    public static boolean replaceAndVerifyTextByXpath(String replaceValue, String xpath, String text) {
        boolean res = false;
        String str = null;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            str = text.replace(replaceValue, "");
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getText().contains(str)) {
                res = true;
            } else {
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleLogger.ErrorLog("Specified element text doesn't match with  the actual value.");
        }
        return res;
    }

    public static void createTeacherRubric(String createTeacherRubricButton, String teacherRubricNameTextbox, String teacherRubricName,
                                           String teacherRubricCKEditor, String teacherRubric, String saveButton, String cancelButton, String rubricSavedAlert) {
        /*Click Teacher Create Button*/
        DriverHelper.clickXpath(createTeacherRubricButton);

        /*Enter the Teacher Rubric Name*/
        DriverHelper.sendKeysXpath(teacherRubricNameTextbox, teacherRubricName);

        /*Enter the Teacher Rubric in CK editor*/
        DriverHelper.sendKeysXpath(teacherRubricCKEditor, teacherRubric);

        /*Click Save button*/
        //DriverHelper.clickXpath(akoAssessmentSetupPage.getTeacherRubricSaveButton());
        JavascriptHelper.click_Xpath(saveButton);

        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" + rubricSavedAlert + "")));

        /*Click Cancel button*/
        //DriverHelper.clickXpath(akoAssessmentSetupPage.getTeacherRubricCancelButton());
        JavascriptHelper.click_Xpath(cancelButton);
    }

    public static void createStudentRubric(String expandIcon, String createStudentRubricButton, String studentRubricNameTextbox, String studentRubricName,
                                           String studentRubricCKEditor, String studentRubric, String saveButton, String cancelButton, String rubricSavedAlert) {
        JavascriptHelper.scrollIntoView(expandIcon);

        /*Click Student Create Button*/
        DriverHelper.clickXpath(createStudentRubricButton);

        /*Enter the Student Rubric Name*/
        DriverHelper.sendKeysXpath(studentRubricNameTextbox, studentRubricName);

        /*Enter the Student Rubric in CK editor*/
        DriverHelper.sendKeysXpath(studentRubricCKEditor, studentRubric);

        /*Click Save button*/
        //DriverHelper.clickXpath(akoAssessmentSetupPage.getTeacherRubricSaveButton());
        JavascriptHelper.click_Xpath(saveButton);

        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" + rubricSavedAlert + "")));

        /*Click Cancel button*/
        //DriverHelper.clickXpath(akoAssessmentSetupPage.getTeacherRubricCancelButton());
        JavascriptHelper.click_Xpath(cancelButton);
    }

    //To Upload File
    public static void UploadFile(WebElement uploadElement, String uploadFilePath) {
        try {
            System.out.println("uploadFilePath: " + uploadFilePath);
            uploadElement.click();
            Thread.sleep(3000);
            //Store the location of the file in clipboard
            //Clipboard
            StringSelection strSel = new StringSelection(uploadFilePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSel, null);

            //Create an object for robot class
            Robot robot = new Robot();
            //Control key in the keyboard
            //Ctrl+V
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (InterruptedException ie) {
            //log.info("InterruptedException handled.....");
        } catch (AWTException awt) {
            //log.info("AWTException handled");
        } catch (Exception e) {
            //log.info("Exception handled");
        }
    }

    public static void uploadRubric(String uploadButtonXpath, String rubricNameXpath, String uploadRubricDropZone, String rubricName,
                                    String uploadFilePath, String uploadSuccessIcon, String saveRubricXpath, String savedAlertXpath) {
        //Helper objHelper = new Helper();
        DriverHelper.clickXpath(uploadButtonXpath);

        waitUntilLoaderInvisible();

        DriverHelper.sendKeysXpath(rubricNameXpath, rubricName);

        JavascriptHelper.scrollIntoView(uploadButtonXpath);

        WebElement uploadDropZone = BrowserInitHelper.getInstance().findElement(By.xpath(uploadRubricDropZone));

        //Call hepler method for File Upload
        UploadFile(uploadDropZone, uploadFilePath);

        waitUntil(uploadSuccessIcon);

        DriverHelper.clickXpath(saveRubricXpath);

        waitUntilLoaderInvisible();

        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(savedAlertXpath)));
        //DriverHelper.clickXpath(closeAlertXpath);

        //DriverHelper.clickXpath(uploadButtonXpath);
    }

    public static void waitTill(long timeToWait) {
        try {
            Thread.sleep(timeToWait * 1000);
        } catch (Exception e) {
            System.out.println("Exception handled for method - waitTill");
        }
    }

    public static boolean checkElementDisplayByXpath(String xpath, String elementName) {
        boolean res = false;
        try {
             BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isDisplayed()) {
                res = true;
                ConsoleLogger.SuccessLog("" + elementName + " is displayed.....");
            } else
                return res;
        } catch (Exception e) {
            ConsoleLogger.DebugLog("" + elementName + " is NOT displayed.....!!!");
        }
        return res;
    }

    public static boolean checkElementDisplayByCSS(String locator, String elementName) {
        boolean value = false;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));
            value = true;
            ConsoleLogger.SuccessLog("" + elementName + " is displayed.....");
        } catch (Exception e) {
            System.out.println("Exception handled for " + e);
            ConsoleLogger.FailedTestCase("" + elementName + " is NOT displayed.....!!!");
        }
        return value;
    }

    /*To verify links  using http response */
    /*connecting to the url ,getting response code and after this closing the connection*/
    public static boolean validateUrlResponseCodeByXpath(String xpath) {
        boolean res = false;
        List<WebElement> links = null;
        try {
            links = BrowserInitHelper.getInstance().findElements(By.xpath(xpath));
            for (int i = 0; i < links.size(); i++) {
                System.out.println("LINKS ==>" + links.get(i));
                URL url = new URL(links.get(i).getAttribute("href"));
                //System.out.println("url :- " + url);
                /*System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@   ==> "+url);*/
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int code = connection.getResponseCode();
                // System.out.println("Response code of the URL is " + code);
                if (code >= 200 && code < 404) {
                    res = true;
                    ConsoleLogger.SuccessLog("Url response code  match with expected response code.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ConsoleLogger.DebugLog("Url response code doesn't match with expected response code.");
        }
        return res;
    }

    public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        return huc.getResponseCode();
    }

    public static String getClassNameByXpath(String xpath) {
        String res = null;
        try {
            String elementClassName = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getAttribute("class");
            res = elementClassName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static boolean verifyDisplay(String xpath) {
        boolean res = false;
        try {
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isDisplayed()) {
                res = true;
            }
        } catch (Exception e) {
            //  ConsoleLogger.DebugLog(xpath + " Unable to find the specified " + xpath + " element.");
        }
        return res;
    }
    public static boolean verifyDisplayByXpath(String xpath) {
        boolean res = false;
        try {
            BrowserInitHelper.getMaxWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isDisplayed()) {
                res = true;
            }
        } catch (Exception e) {
           ConsoleLogger.DebugLog(xpath + " Unable to find the specified " + xpath + " element.");
        }
        return res;
    }

    public static boolean verifyAndCompare(String xpath, String text) {
        boolean value = false;
        try {
            BrowserInitHelper.getMaxWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            String msg=BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getText();
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getText().contains(text)) {
                value = true;
            } else {
                value = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            ConsoleLogger.DebugLog("Exception Handled.");
        }
        return value;
    }


    public static void clearTextBox(String xpath) {
        WebElement textBox = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
        textBox.clear();
    }

    public static void verifyDropDownOptions(String roleNameType, String locator) {
        int dropdownCount = 0;
        String dropdowns = DriverHelper.getReplacedLocator(roleNameType, locator);
        DriverHelper.waitTill(5);
        List<WebElement> actualOptions = BrowserInitHelper.getInstance().findElements(By.xpath(dropdowns));
        boolean isExist = false;
        try {
            for (int i = 0; i < actualOptions.size(); i++) {
                String dropdownText = actualOptions.get(i).getText();
                dropdownCount++;
            }
            if (actualOptions.size() == dropdownCount) {
                isExist = true;
            }
            //System.out.println(actualOptions.size() +" " +"actualsize");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //milli seconds
    public static void wait(double ms) {
        try {
            Thread.sleep((int) ms * 1000);//seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitFluentByXPath(WebDriver driver, final String xPath) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(dynamic.getWaitTime()))
                .pollingEvery(Duration.ofMillis(5000)).ignoring(Exception.class);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
    }



    public static boolean CheckTextByXpath(String Locator, String Elementname, String csvData) {
        boolean value = false;
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.valueOf(Locator))));
        String text = BrowserInitHelper.getInstance().findElement(By.xpath(String.valueOf(Locator))).getText();
        System.out.println(text);
        if (csvData.equalsIgnoreCase(text)) {
            ConsoleLogger.SuccessLog(Elementname + " " + "is matching as per the test data");
            value = true;
        } else {
            ConsoleLogger.FailedTestCase(Elementname + " " + "is not matching as per the test data");
        }
        return value;
    }

    public static void navigateFromLeftToRightTab() {
        try {
            DriverHelper.waitTill(2);
            Actions action = new Actions(BrowserInitHelper.getInstance());
            action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).build().perform();
            DriverHelper.waitTill(2);
        } catch (Exception e) {
            System.out.println("Exception handled for method - navigateFromLeftToRightTab");
        }
    }

    public static void clickXpath(String xpath) {
        highlight(BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))));
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    public static void sendKeysXpath(String xpath, String text) {
        highlight(BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))));
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).clear();
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).sendKeys(text);
    }

    // To delete files
    public static String getDeletePath() {
        String os = null;
        String dir = System.getProperty("user.dir");
        String deletePath = null;

        os = System.getProperty("os.name");
        try {
            if (os.contains("Windows")) {
                deletePath = dir + "\\log";
            } else {
                deletePath = dir + "/log";
            }
        } catch (Exception e) {
            System.out.println("Exception handled for method - getDownloadsPath...");
        }
        ConsoleLogger.DebugLog("deletePath ==>" + deletePath);
        return deletePath;
    }



    public static String getAttributeClassNameByXpath(String xpath) {
        String value = null;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            value = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getAttribute("class");
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    public static void moveAndClick(String xpath) {
        try {
            WebElement element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
            Actions actions = new Actions(BrowserInitHelper.getInstance());
            actions.moveToElement(element).click().perform();
            waitTill(3);
        } catch (Exception e) {
            System.out.println("Exception handled for method - moveAndClick" + e);
        }
    }

    public static String getStyleOfElement(String xPathValue) {
        String style = "";

        try {
            style = BrowserInitHelper.getInstance().findElement(By.xpath(xPathValue)).getAttribute("style");
        } catch (Exception e) {
            System.out.println("Exception handled for method - getStyleOfElement" + e);
        }
        return style;
    }

    public static void waitUntilLoaderVisibleAndInvisible() {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divPageLoading']")));
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='divPageLoading']")));
    }

    public static String getAttributeIdByXpath(String xpath) {
        String value = null;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            value = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getAttribute("id");
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    public static void selectAvailableRubric(String selectAvailableRubricButton, String availableRubricsTextbox, String rubricName,
                                             String searchIcon, String selectRubricsButton, String exitButton, String saveButton, String savedAlertXpath) {
        try {
            //DriverHelper.clickXpath(selectAvailableRubricButton);
            JavascriptHelper.click_Xpath(selectAvailableRubricButton);

            waitUntilLoaderInvisible();

            Thread.sleep(3000);

            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(availableRubricsTextbox)));

            DriverHelper.sendKeysXpath(availableRubricsTextbox, rubricName);

            JavascriptHelper.click_Xpath(searchIcon);
            //DriverHelper.clickXpath(searchIcon);

            waitUntilLoaderInvisible();

            Thread.sleep(3000);

            BrowserInitHelper.getInstance().findElement(By.xpath("//a[text() = '" + rubricName + "']/parent::td/preceding-sibling::td/input")).click();

            Thread.sleep(2000);

            DriverHelper.clickXpath(selectRubricsButton);

            //DriverHelper.clickXpath(exitButton);

            JavascriptHelper.click_Xpath(saveButton);

            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(savedAlertXpath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scrollTillElementDisplayByXpath(String strXpath) {
        boolean footerCheck = true;
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        while (footerCheck) {
            js.executeScript("return window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(2000);
                BrowserInitHelper.getInstance().findElement(By.xpath(strXpath));
                footerCheck = false;
            } catch (Exception e) {
                System.out.println("Scrolling failed for the element XPATH ...." + strXpath);
            }
        }
    }

    public static void scrollTillElementDisplayById(String strId) {
        boolean footerCheck = true;
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        while (footerCheck) {
            js.executeScript("return window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(2000);
                BrowserInitHelper.getInstance().findElement(By.id(strId));
                footerCheck = false;
            } catch (Exception e) {
                System.out.println("Exception handled for scrollTillElementDisplayById ...." + strId);
            }
        }
    }

    public static boolean verifyDisplayWithAssertionByXpath(String xpath) {
        boolean res = false;
        boolean verifyElementDisplay = false;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            verifyElementDisplay = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isDisplayed();
            if (verifyElementDisplay) {
                ConsoleLogger.SuccessLog(xpath + " Element displayed and validated...");
                res = true;
            } else {
                ConsoleLogger.FailedTestCase(xpath + " Failed to display and validate the specified element...!!");
            }
            Assert.assertTrue(verifyElementDisplay);
        } catch (Exception e) {
            ConsoleLogger.DebugLog(xpath + " Unable to find the specified " + xpath + " element.");
        }
        return res;
    }

    public static void clickXpathByJS(String xpath) {
        boolean clicked = false;
        do {
            waitTill(5);
            try {
                WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
                ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("arguments[0].click();", Element);
            } catch (WebDriverException e) {
                continue;
            } finally {
                clicked = true;
            }
        } while (!clicked);
    }

    public static String getModifiedInputAttributeValue(String xpath, String appendingValue, String textValue) {
        String value = null;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            String result = textValue + appendingValue;
            sendKeysXpath(xpath, result);
            value = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getAttribute("value");
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    public static String getCurrentDateInRequiredPattern(String reqPattern) {
        String strDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(reqPattern);
            Date date = new Date();
            strDate = formatter.format(date);
            System.out.println("Current date in required date format :- " + strDate);
        } catch (Exception e) {
            System.out.println("Exception handled for method - getCurrentDateInRequiredPattern");
        }
        return strDate;
    }

    public static String getPreviousDateInRequiredPattern(String reqPattern, int noOfDays) {
        String strDate = null;
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat(reqPattern);
            cal.add(Calendar.DATE, noOfDays);
            strDate = dateFormat.format(cal.getTime());
        } catch (Exception e) {
            System.out.println("Exception handled for method - getPreviousDateInRequiredPattern");
        }
        return strDate;
    }

    public static String getExactNextMonthDateFromCurrentDate(String reqPattern) {
        String strNextMonthDate = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            Date date = cal.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat(reqPattern);
            strNextMonthDate = formatter.format(date);
            System.out.println("Next Month date :- " + strNextMonthDate);
        } catch (Exception e) {
            System.out.println("Exception handled for method - getExactNextMonthDateFromCurrentdate");
        }
        return strNextMonthDate;
    }

    public static boolean getElementSelectedStatus(String xpath)
    {
        boolean res = false;
        try
        {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            res = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isSelected();
        }catch (Exception e)
        {
            return res;
        }
        return res;
    }

    public static String getElementBorderColor(String xpath) {
        String res = null;
        try {
            BrowserInitHelper.getMinWaiter().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            if (BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).isDisplayed()) {
                res = BrowserInitHelper.getInstance().findElement(By.xpath(xpath)).getCssValue("border-color");
                ConsoleLogger.SuccessLog(xpath + " Element displayed and verified the color for the element");
            } else
                return res;
        } catch (Exception e) {
            ConsoleLogger.DebugLog(xpath + " Unable to find the specified " + xpath + " element.");
        }
        return res;
    }

    //switch to window
    public static void switchWindow() {
        try {
            for (String winHandle : BrowserInitHelper.getInstance().getWindowHandles()) {
                BrowserInitHelper.getInstance().switchTo().window(winHandle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    public static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public static String roundOffTo2DecPlaces(float val)
    {
        return String.format("%.2f", val);
    }

    public static String getCurrentTime(){
        String currentTime = null;
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date date= new Date();
        currentTime= timeFormat.format(date);
        return currentTime;
    }

    public static String getCurrentDateTime(){
        String currentTime = null;
        DateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        Date date= new Date();
        currentTime= timeFormat.format(date);
        return currentTime;
    }

    //To get current Date
    public static String getCurrentDate(){
        String currentDate = null;
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date= new Date();
        currentDate= timeFormat.format(date);
        return currentDate;
    }

    public void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }

    //Method for Verifying Filters
    public static boolean verificationOfElements(String xPath) {
        boolean res = false;
        try {
            BrowserInitHelper.getMaxWaiter().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xPath)));
            for (WebElement element : BrowserInitHelper.getInstance().findElements(By.xpath(xPath))) {
                if (element.isDisplayed()) {
                    res = true;
                }
            }
        } catch (Exception e) {
            ConsoleLogger.DebugLog("Exception Handled for verificationOfElements method");
        }
        return res;
    }

    //Method for DropDown Option
    public static boolean verifyDropDownOption(String xPath) {
        boolean isExist = false;
        try {
            int dropdownCount = 0;
            List<WebElement> actualOptions = BrowserInitHelper.getInstance().findElements(By.xpath(xPath));
            for (int i = 0; i < actualOptions.size(); i++) {
                dropdownCount++;
            }
            if (actualOptions.size() == dropdownCount) {
                isExist = true;
            }
        } catch (Exception e) {
            ConsoleLogger.DebugLog("Exception Handled for verifyDownLoadOption method");
        }
        return isExist;
    }

    public static void testExecution(boolean tcValue, String tcDescription) {
        if (tcValue) {
            LoginTest.passedCount++;
            dependant = true;
            ConsoleLogger.SuccessLog("Test case : Passed - " + tcDescription);
        } else {
            LoginTest.failedCount++;
            dependant = false;
            ConsoleLogger.FailedTestCase("Test case : Failed - " + tcDescription);
        }
    }

    public static boolean returnValue(boolean value) {
        if (!value) {
            dependant = true;
        }
        return dependant;
    }

    public static void testExecution(boolean tcValue) {
        if (tcValue) {
            LoginTest.dependant = true;
        } else {
            LoginTest.dependant = false;
        }
    }
}
