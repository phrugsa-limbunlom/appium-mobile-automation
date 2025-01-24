package test_case;

import static helper.DeviceHelper.logout;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;
import static helper.DeviceHelper.perform_click_on_checkbox;
import static helper.DeviceHelper.perform_click_sign_up_button;
import static helper.DeviceHelper.validate_invalid_information;
import static helper.FileReaderHelper.read_test_file_for_registration;

import config.AppiumConfig;
import data.Registration;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

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


public class RegistrationENTest {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private List<Registration> registrationList;

    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {
        // Read test data from file
        registrationList = read_test_file_for_registration();

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
    public void successfully_register_a_new_user() {

        for (Registration registration : registrationList) {
            // Navigate to registration screen
            By registerButton = AppiumBy.accessibilityId("<ID>");
            perform_click_sign_up_button(registerButton, driver);

            WebElement acknowledgeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));

            acknowledgeButton.click();

            // Fill in registration form
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")));
            emailField.click();
            emailField.sendKeys(registration.getEmail());

            // Generate a unique email address

            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
            passwordField.click();
            passwordField.sendKeys(registration.getPassword());

            WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)")));
            confirmPasswordField.click();
            confirmPasswordField.sendKeys(registration.getPassword());

            WebElement agentCodeField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(3)")));
            agentCodeField.click();
            agentCodeField.sendKeys(registration.getAgentcode());

//        ((AndroidDriver) driver).hideKeyboard();

            WebElement membership = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            membership.click();

            WebElement accept_requirement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(24)")));
            accept_requirement.click();

            WebElement signup = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.view.View[@content-desc=\"Register\"])[2]")));
            signup.click();

            // KYC Step 1
            WebElement otp = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText")));
            otp.click();
            otp.sendKeys("000000");

            WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            confirm.click();

            By privacy_policy = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(14)");
            perform_click_on_checkbox(privacy_policy, driver);

            WebElement publicity_information = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            publicity_information.click();

            WebElement accept = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            accept.click();

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
                    pin.click();
                }
            }

            logout(driver, wait);
        }

    }

    @Test
    public void register_a_new_user_with_invalid_information() {

        // Navigate to registration screen
        By registerButton = AppiumBy.accessibilityId("<ID>");
        perform_click_sign_up_button(registerButton, driver);

        WebElement acknowledgeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));

        acknowledgeButton.click();

        // Fill in registration form
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")));
        emailField.click();
        emailField.sendKeys("testuser14");

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        passwordField.click();
        passwordField.sendKeys("Password");

        WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)")));
        confirmPasswordField.click();
        confirmPasswordField.sendKeys("123");

        WebElement agentCodeField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(3)")));
        agentCodeField.click();
        agentCodeField.sendKeys("12345");

//        ((AndroidDriver) driver).hideKeyboard();

        WebElement membership = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        membership.click();

        WebElement accept_requirement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(24)")));
        accept_requirement.click();

        WebElement signup = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.view.View[@content-desc=\"<ID>\"])[2]")));
        signup.click();

        // Validate invalid information
        By incorrect_email = AppiumBy.accessibilityId("<ID>");
        validate_invalid_information(incorrect_email, driver, wait);

        By incorrect_password = AppiumBy.accessibilityId("<ID>");
        validate_invalid_information(incorrect_password, driver, wait);

        By incorrect_password2 = AppiumBy.accessibilityId("<ID>");
        validate_invalid_information(incorrect_password2, driver, wait);

        boolean otp_displayed = true;

        try {
            // KYC step 1
            driver.findElement(AppiumBy.accessibilityId("<ID>"));
        } catch (Exception e) {
            otp_displayed = false;
        }
        Assertions.assertFalse(otp_displayed, "The account should not be registered");
    }

    @Test
    public void register_a_new_user_with_existing_user() {
        // Navigate to registration screen
        By registerButton = AppiumBy.accessibilityId("<ID>");
        perform_click_sign_up_button(registerButton, driver);

        WebElement acknowledgeButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));

        acknowledgeButton.click();

        // Fill in registration form
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")));
        emailField.click();
        emailField.sendKeys("testuser1@example.com");

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        passwordField.click();
        passwordField.sendKeys("@Password123");

        WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)")));
        confirmPasswordField.click();
        confirmPasswordField.sendKeys("@Password123");

        WebElement agentCodeField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(3)")));
        agentCodeField.click();
        agentCodeField.sendKeys("12345");

//        ((AndroidDriver) driver).hideKeyboard();

        WebElement membership = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        membership.click();

        WebElement accept_requirement = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(24)")));
        accept_requirement.click();

        WebElement signup = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.view.View[@content-desc=\"Register\"])[2]")));
        signup.click();

        // Validate user
        WebElement existing_user = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        Assertions.assertTrue(existing_user.isDisplayed(), "The existing account should not be registered");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
