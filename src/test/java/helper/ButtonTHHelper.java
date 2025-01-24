package helper;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;

public class ButtonTHHelper {

    public static void home_button(WebDriverWait wait) {
        WebElement home = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\")]")));
        home.click();
    }

    public static void profile_button(WebDriverWait wait) {
        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\")]")));
        profile.click();
    }

    public static void portfolio_button(WebDriverWait wait) {
        WebElement portfolio = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\")]")));
        portfolio.click();
    }

    public static void invest_button(WebDriverWait wait) {
        WebElement invest = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\") and not(contains(@content-desc, \"<ชื่อ>\")) and not(contains(@content-desc, \"<ชื่อ>\"))]")));
        invest.click();
    }

    public static void invest_button2(WebDriverWait wait) {
        WebElement invest = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\")]")));
        invest.click();
    }

    public static void close_button(WebDriverWait wait) {
        WebElement close = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ไอดี>")));
        close.click();
    }

    public static void back_button(WebDriverWait wait) {
        WebElement back = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(0)")));
        back.click();
    }

    public static void back_button1(WebDriverWait wait) {
        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button.click();
    }

    public static void back_button_to_invest(WebDriverWait wait) {
        WebElement back_button_to_invest = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)")));
        back_button_to_invest.click();
    }

    public static void confirm_button(WebDriverWait wait) {
        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ไอดี>")));
        confirm.click();
    }

    public static void history_button(WebDriverWait wait) {
        WebElement history = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ชื่อ>\")]")));
        history.click();
    }
}
