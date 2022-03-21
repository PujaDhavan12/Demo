package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JavascriptHelper {

    //Logger and WebDrivers
    //private static final Logger logger = LoggerFactory.getLogger(JavascriptHelper.class);

    public static void highlight(WebElement element) { // add int duration in parameters passed
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 4px solid red;');", element);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', 'background: white; border: 1px solid black;');", element);
    }

    public static void clickID_JS(String ID) {
        try {
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return !!jQuery(\"#" + ID + "\").click();");
        } catch (JavascriptException je) {
          //  logger.info("JavascriptException handled for method - clickID_JS");
        }
    }

    public static void clickXpath_JS(String xpath) {
        System.out.println("Clicking " + xpath);
        boolean clicked = false;
        do {
            try {
                BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
                ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("arguments[0].click();", Element);
            } catch (NoSuchElementException ne) {
                continue;
            } catch (WebDriverException e) {
                continue;
            } finally {
                clicked = true;
            }
        } while (!clicked);
    }

    public static void sendValuesJavaScriptById(String ID, String strValue) {
        ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return document.getElementById('" + ID + "').value='" + strValue + "';");
    }

    public static void closeBrowserPopup() {
        ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("window.onbeforeunload = function(e){};");
    }

    public static void ScrollTillElement_Javascript(String xpath) {
        WebElement element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y);
    }

    public static void scrollBy_JS(String xpath) {
        BrowserInitHelper.getMinWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        WebElement Div_Element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));

        JavascriptExecutor jse = (JavascriptExecutor) BrowserInitHelper.getInstance();
        jse.executeScript("arguments[0].scrollIntoView(true)", Div_Element);
    }

    public static void scrollBy_JSWithValue(int value) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) BrowserInitHelper.getInstance();
            jse.executeScript("window.scrollBy(0,-" + value + ");");
            DriverHelper.WaitUntilLoad(1000);
        } catch (Exception e) {
            System.out.println("Exception handled for method - scrollBy_JSWithValue");
        }
    }

    public static void waitTillPageLoad() {
        try {
            BrowserInitHelper.getInstance().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        } catch (TimeoutException te) {

        }
    }

    public static void scrollIntoView(String xpath) {
        try {
            WebElement element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("arguments[0].scrollIntoView(true);", element);
            scrollUp();
        } catch (Exception e) {
           // System.out.println("Exception handled....");
        }
    }

    public static String getItemCountValue() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
            Object val = js.executeScript("return document.querySelector('#questions-list>div>div>input').value;");
            return val.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void MultipleClickByID_Javascript(String value) {
        //split value by comma and add in list
        List<String> SplittedList = Arrays.asList(value.split(","));

        for (int i = 0; i < SplittedList.size(); i++) {
            JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
            js.executeScript("return document.getElementById('" + SplittedList.get(i) + "').click();");
        }
    }

    public static void scrollElementIntoView(WebElement Element) {
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("arguments[0].scrollIntoView()", Element);
    }

    public static void scrollUp(){
        try {
            JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
            js.executeScript("window.scrollBy(0,-100)");
            Thread.sleep(3000);
        }
        catch (Exception e){
           // e.printStackTrace();
        }
    }

    public static void click_Xpath(String xPath) {
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(xPath));
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("arguments[0].click();", Element);
    }

    public static void sendValuesByXpath_JS(String xPath, String value) {
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.xpath(xPath));
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("arguments[0].value='" + value + "';", Element);
    }

    public static void sendTextByID(String ID, String value) {
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("return document.getElementById('" + ID + "').value = '" + value + "';");
    }


    public static void scrollTop() {
        try {
            Thread.sleep(2000);
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("window.scrollTo(0,0);");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception handled for method - scrollTop");
        }
    }

    public static void scrollDownByPixels(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("scroll(0, " + pixels + ");");
    }

    public static String getCheckboxStatus(String checkBoxID) {
        Object val = null;
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        val = js.executeScript("return document.getElementById('" + checkBoxID + "').checked;");
        return val.toString();
    }

    public static void scrolBottom() {
        try {
            Thread.sleep(2000);
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("window.scrollTo(0,document.body.scrollHeight);");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Exception handled for method - scrollTop");
        }
    }

    public static void launchNewTab() {
        try {
            DriverHelper.waitTill(2);
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("window.open('about:blank','_blank');");
            DriverHelper.waitTill(2);
        } catch (Exception e) {
            System.out.println("Exception handled for method - scrollTop");
        }
    }

    public static void scrollIntoViewById(String id) {
        WebElement Element = BrowserInitHelper.getInstance().findElement(By.id(id));
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        js.executeScript("arguments[0].scrollIntoView()", Element);
    }

    public static String getToggleButtonStateByXpath(String xpath) {
        return (((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return document.getElementByXpath('" + xpath + "').checked;")).toString();
    }

    public static String getToggleButtonState(String ID) {
        return (((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return document.getElementById('" + ID + "').checked;")).toString();
    }

    public static boolean compareAndCheckElementById(String eleId, String reqStatus) {
        boolean res = false;
        try {
            String elementStatus = JavascriptHelper.getToggleButtonState(eleId);
            if (elementStatus.equalsIgnoreCase(reqStatus)) {
                System.out.println("For the element -- " + eleId + " -- default status -- " + reqStatus + " -- & Required status  -- " + reqStatus + " -- both are matching");
                res = true;
            } else {
                DriverHelper.waitTill(1);
                JavascriptHelper.ClickByID_Javascript(eleId);
                System.out.println("Required to change the -- " + eleId + " -- element from default status -- " + reqStatus + " -- to Required status  -- " + reqStatus + "");
            }
        } catch (Exception e) {
            System.out.println("Exception handled for verifyAndCheckElementById for the id :- " + eleId);
        }
        return res;
    }

    public static void setTextboxValue_ID(String ID, String value) {
        JavascriptExecutor js = (JavascriptExecutor) BrowserInitHelper.getInstance();
        //js.executeScript("return document.getElementById('" + value + "').value = '';");
        js.executeScript("return document.getElementById('" + ID + "').value = '" + value + "';");
    }

    public static void scrollIntoViewWebElement(WebElement element) {
        try {
            //WebElement element = BrowserInitHelper.getInstance().findElement(By.xpath(xpath));
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("arguments[0].scrollIntoView();", element);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception Handled");
        }
    }

    public static void clickWebelement_JS(WebElement element) {
        try {
            ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("arguments[0].click();", element);
           // System.out.println("Clicking on element with using java script click");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ClickByID_Javascript(String ID) {
        ((JavascriptExecutor) BrowserInitHelper.getInstance()).executeScript("return document.getElementById('" + ID + "').click();");
    }

    public static void scrollIntoViewAndClick(WebElement element) {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) BrowserInitHelper.getInstance();
            // now execute query which actually will scroll until that element is not appeared on page.
            je.executeScript("arguments[0].scrollIntoView(true);", element);
            DriverHelper.waitTill(5);
        } catch (Exception e) {
            System.out.println("Exception handled for scrollIntoViewAndClick" + e);
        }
    }

}
