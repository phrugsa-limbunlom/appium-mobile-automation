package test_case;

import static helper.DeviceHelper.login;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;
import static helper.DeviceHelper.perform_click_on_forget_password_button;
import static helper.DeviceHelper.validate_invalid_information;
import static helper.FileReaderHelper.read_test_file_for_login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import config.AppiumConfig;
import data.Login;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class
LogInENTest {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private List<Login> loginList;

    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {

        // Read test data from file
        loginList = read_test_file_for_login();

        UiAutomator2Options options = AppiumConfig.getOptions();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        // Set implicit wait timeout to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Set explicit wait timeout to 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        pass_the_welcome_page_with_English_language(wait);

        Thread.sleep(500);

    }

    @Test
    public void successfully_login_an_account_and_setup_pin_code() {
       for(Login login : loginList) {
           login(driver, wait, login.getEmail(), login.getPassword());
       }
    }

    @Test
    public void login_an_account_with_invalid_information() {
        String email = "testttt@example.com";
        String password = "Password";

        login(driver, wait, email, password);

//        ((AndroidDriver) driver).hideKeyboard();

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        loginButton.click();

        By invalid_information = AppiumBy.accessibilityId("<ID>");
        validate_invalid_information(invalid_information, driver, wait);

    }

    @Test
    public void successfully_forget_and_reset_password() {

        By forget_password = AppiumBy.accessibilityId("<ID>");
        perform_click_on_forget_password_button(forget_password, driver);

        WebElement valid_email_address = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")));
        valid_email_address.click();
        valid_email_address.sendKeys("<ID>");

//        ((AndroidDriver) driver).hideKeyboard();

        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        WebElement otp = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")));
        otp.click();
        otp.sendKeys("000000");

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm.click();

        WebElement new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[3]/android.widget.EditText")));
        new_password.click();
        new_password.sendKeys("<ID>");

        WebElement confirm_new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        confirm_new_password.click();
        confirm_new_password.sendKeys("<ID>");

        WebElement confirm_edits = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm_edits.click();

        WebElement back_to_home = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        back_to_home.click();

    }

    @Test
    public void forget_password_but_password_not_match() {

        By forget_password = AppiumBy.accessibilityId("<ID>");
        perform_click_on_forget_password_button(forget_password, driver);

        WebElement valid_email_address = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")));
        valid_email_address.click();
        valid_email_address.sendKeys("testuser14@example.com");

        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        WebElement otp = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")));
        otp.click();
        otp.sendKeys("000000");

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm.click();

        WebElement new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[3]/android.widget.EditText")));
        new_password.click();
        new_password.sendKeys("@Password1234");

        WebElement confirm_new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        confirm_new_password.click();
        confirm_new_password.sendKeys("@Password");

        WebElement password_do_not_match = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("<ID>")));
        Assertions.assertTrue(password_do_not_match.isDisplayed(), "Password do not match should be displayed");


        WebElement confirm_edits = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm_edits.click();

        boolean confirm_edits_displayed = confirm_edits.isSelected();
        Assertions.assertFalse(confirm_edits_displayed, "Confirm edits should not be displayed");

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
