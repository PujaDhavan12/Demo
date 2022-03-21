package helpers;
import utils.Dynamic;
import utils.Secure;
import utils.Static;
import com.google.common.base.Stopwatch;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Helper Class for BrowserInitHelper
 **/
public class BrowserInitHelper {
    private static String browser, chrome, firefox;
    private static Boolean headless;
    private static int waitTime;
    public static Dynamic dynamic = new Dynamic();
    public static Static staticPage = new Static();
    public static Secure secure = new Secure();
    private static WebDriver driver = null;
    private static FirefoxProfile profile = new FirefoxProfile();
    public static boolean loggingEnabled = true;
    public static FirefoxOptions options = new FirefoxOptions();
    public static Stopwatch stopwatch;
    /**
     * LoggerFactory
     */
    //private static final Logger log = LoggerFactory.getLogger(BrowserInitHelper.class);
    /**
     * Creates manager  of Chrome
     */
    public static void setup() {
        stopwatch = Stopwatch.createStarted();
        dynamic.readDynamicProperties();
        secure.readSecureProperties();
        staticPage.readStaticProperties();
        browser = dynamic.getBrowser();
        firefox = staticPage.getFirefox();
        if (browser.equals("chrome")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
        } else if (browser.equals("firefox") && firefox.length() != 0) {
            loggingEnabled = false;
            System.setProperty("webdriver.gecko.driver", firefox);
            profile.setPreference("devtools.console.stdout.content", true);
            options.setProfile(profile);
            driver = new FirefoxDriver(options);
        } else if (browser.equals("firefox") && firefox.length() == 0) {
            loggingEnabled = false;
            profile.setPreference("devtools.console.stdout.content", true);
            FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        } else if (browser.equals("edge")) {
            loggingEnabled = false;
            EdgeDriverManager.getInstance(DriverManagerType.EDGE).setup();
        } else if (browser.equals("safari")) {
            loggingEnabled = false;
            driver = new SafariDriver();
        }
    }
    /**
     * Tear down Chrome Driver
     */
    public static void tearDown() {
        stopwatch.stop();
        getInstance().quit();
        //killChromeProcess();
    }
    /**
     * Create an instance of Chrome WebDriver
     */
    private static class ChromeWebDriverHolder {
        //private static final WebDriver INSTANCE = new ChromeDriver(getChromeOptions());
        private static WebDriver INSTANCE = setInstance();
        private static WebDriver setInstance() {
            if (dynamic.getDocker().equalsIgnoreCase("true")) {
                INSTANCE = testChromeDocker();
            } else {
                INSTANCE = new ChromeDriver(getChromeOptions());
            }
            return INSTANCE;
        }
    }
    public static WebDriver testChromeDocker() {
        WebDriver INSTANCE = null;
        Dynamic dynamicVar = new Dynamic();
        DesiredCapabilities cap = new DesiredCapabilities();
        //String driverPath = System.getProperty("user.dir") + "/exe/chromedriver.exe";
        //System.setProperty("webdriver.chrome.driver", driverPath);
        cap.setBrowserName(BrowserType.CHROME);
        staticPage.readStaticProperties();
        dynamic.readDynamicProperties();
        browser = dynamic.getBrowser();
        headless = dynamic.getHeadless();
        chrome = staticPage.getChrome();
        if (!chrome.equals("")) {
            System.setProperty("webdriver.chrome.driver", chrome);
        }
        ChromeOptions options = new ChromeOptions();
        if (browser.equals("chrome") && headless == true) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("window-size=1600,1600");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("window-size=1600,1600");
            options.addArguments("--disable-extensions");
        }
        try {
            INSTANCE = new RemoteWebDriver(new URL(dynamic.getRemoteUrl()), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return INSTANCE;
    }
    private static class FirefoxWebDriverHolder {
        private static final WebDriver INSTANCE = new FirefoxDriver();
    }
    private static class EdgeWebDriverHolder {
        private static final WebDriver INSTANCE = new EdgeDriver();
    }
    private static class SafariWebDriverHolder {
        private static final WebDriver INSTANCE = new SafariDriver();
    }
    public static ChromeOptions getChromeOptions() {
        staticPage.readStaticProperties();
        dynamic.readDynamicProperties();
        browser = dynamic.getBrowser();
        headless = dynamic.getHeadless();
        chrome = staticPage.getChrome();
        //Path chromePath = Paths.get(chrome);
        if (!chrome.equals("")) {
            System.setProperty("webdriver.chrome.driver", chrome);
        }
        ChromeOptions options = new ChromeOptions();
        if (browser.equals("chrome") && headless == true) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("window-size=1600,1600");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
        } else {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            options.addArguments("--no-sandbox");
            options.addArguments("window-size=1600,1600");
            options.addArguments("--disable-extensions");
        }
        return options;
    }
    public static FirefoxOptions getFirefoxOptions() {
        dynamic.readDynamicProperties();
        staticPage.readStaticProperties();
        browser = dynamic.getBrowser();
        headless = dynamic.getHeadless();
        firefox = staticPage.getFirefox();
        System.out.println("Firefox Gecko Path==>" + firefox);
        if (!firefox.equals("")) {
            System.setProperty("webdriver.gecko.driver", firefox);
        }
        FirefoxOptions options = new FirefoxOptions();
        if (browser.equals("firefox") && headless == true) {
            //options.addArguments("window-size=1600,1600");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
        } else {
            //options.addArguments("window-size=1600,1600");
            options.addArguments("--disable-extensions");
        }
        return options;
    }
    public static WebDriverWait getWaiter() {
        return new WebDriverWait(getInstance(), 30);
    }

    public static WebDriver getInstance() {
        if (browser.equals("chrome")) {
            return ChromeWebDriverHolder.INSTANCE;
        } else if (browser.equals("firefox") && firefox.length() == 0) {
            return FirefoxWebDriverHolder.INSTANCE;
        } else if (browser.equals("firefox") && firefox.length() != 0) {
            driver.manage().window().maximize();
            return driver;
        } else if (browser.equals("edge")) {
            return EdgeWebDriverHolder.INSTANCE;
        } else if (browser.equals("safari")) {
//            driver.manage().window().maximize();
            return driver;
        } else {
            return ChromeWebDriverHolder.INSTANCE;
        }
    }
    public static void waitUntil(String xpath) {
        BrowserInitHelper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }
    public void assertion(String xpath, String expected) {
        Assert.assertEquals(getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText().trim(), expected);
    }
    public static void killChromeProcess() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                Process process = Runtime.getRuntime().exec("TASKKILL /F /IM chrome.exe");
                System.out.println("Process - ChromeDriver ==>" + process);
                DriverHelper.waitTill(2000);
                process.destroy();
                Process process_chromeDriver = Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
                System.out.println("Process - ChromeDriver ==>" + process_chromeDriver);
                DriverHelper.waitTill(2000);
                process_chromeDriver.destroy();
                System.out.println("<== Process - ChromeDriver destroyed - Windows ==>");
            }
            if (System.getProperty("os.name").contains("Ubuntu") || System.getProperty("os.name").contains("ubuntu")) {
                Process process = Runtime.getRuntime().exec("killall -9 chrome");
                System.out.println("Process - ChromeDriver ==>" + process);
                DriverHelper.waitTill(2000);
                process.destroy();
                Process process_chromeDriver = Runtime.getRuntime().exec("killall -9 chromedriver");
                System.out.println("Process - ChromeDriver ==>" + process_chromeDriver);
                DriverHelper.waitTill(2000);
                process_chromeDriver.destroy();
                System.out.println("<== Process - ChromeDriver destroyed - Ubuntu ==>");
            }
            DriverHelper.waitTill(2000);
        } catch (IOException e) {
            System.out.println("IOException handled for method - tearDown");
        } catch (Exception e) {
            System.out.println("Exception handled for method - tearDown");
        }
    }
    // To check files are downloaded
    public static void deleteFile(String deletePath) {
        try {
            File dir = new File(deletePath);
            File[] dirContents = dir.listFiles();
            for (int i = 0; i < dirContents.length; i++) {
                dirContents[i].delete();
            }
        } catch (Exception e) {
            System.out.println("Exception handled for deleting file");
        }
    }

    public static WebDriverWait getWaiterMaximumTime() {
        return new WebDriverWait(getInstance(), 60);
    }

    public static WebDriverWait getMaxWaiter() {
        return new WebDriverWait(getInstance(), 90);
    }

    public static WebDriverWait getMinWaiter() {
        return new WebDriverWait(getInstance(), 30);
    }
}