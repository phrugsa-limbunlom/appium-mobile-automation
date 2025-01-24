package test_case;

import static helper.ButtonHelper.close_button;
import static helper.ButtonHelper.confirm_button;
import static helper.ButtonHelper.profile_button;
import static helper.DeviceHelper.login;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;
import static helper.FileReaderHelper.read_test_file_for_login_of_one_user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import config.AppiumConfig;
import data.Login;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class ProfileENTest {
    private AndroidDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {

        // Read test data from file
        Login loginData = read_test_file_for_login_of_one_user();

        UiAutomator2Options options = AppiumConfig.getOptions();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        // Set implicit wait timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        // Set explicit wait timeout
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        pass_the_welcome_page_with_English_language(wait);

        Thread.sleep(500);

        login(driver, wait, loginData.getEmail(), loginData.getPassword());

    }

    @Test
    public void successfully_click_inquire_information() {

        profile_button(wait);

        WebElement inquire_for_information = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        inquire_for_information.click();

        WebElement contact_an_officer = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        contact_an_officer.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        back_button.click();

        WebElement learn_more_about_investing = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        learn_more_about_investing.click();

        WebElement back_button1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button1.click();

        WebElement back_button2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button2.click();

    }

    @Test
    public void successfully_click_customer_support_and_privileges() {
        profile_button(wait);

        WebElement customer_support_and_privileges = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        customer_support_and_privileges.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button.click();

    }

    @Test
    public void successfully_click_investor_classroom() {
        profile_button(wait);

        WebElement investor_class_room = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        investor_class_room.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button.click();

    }

    @Test
    public void successfully_setting_bank_account() {
        profile_button(wait);

        WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        settings.click();

        WebElement bank_account_information = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        bank_account_information.click();

        WebElement edit_bank_account = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        edit_bank_account.click();

        WebElement bank = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        bank.click();

        WebElement bank_option = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        bank_option.click();

        WebElement bank_account_number = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[@text=\"1234567890\"]")));
        bank_account_number.click();

        driver.hideKeyboard();

        WebElement upload_ID_card = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Upload document\"])[1]")));
        upload_ID_card.click();

        WebElement image1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        image1.click();

        WebElement photo1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.google.android.providers.media.module:id/icon_thumbnail\"]")));
        photo1.click();


        WebElement upload_passbook = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"<ID>\"])[2]")));
        upload_passbook.click();

        WebElement image2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        image2.click();

        WebElement photo2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.google.android.providers.media.module:id/icon_thumbnail\"]")));
        photo2.click();


        WebElement upload_photo_with_ID_card = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        upload_photo_with_ID_card.click();

        WebElement image3 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        image3.click();

        WebElement photo3 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.google.android.providers.media.module:id/icon_thumbnail\"]")));
        photo3.click();

        WebElement confirm_edits = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm_edits.click();

        confirm_button(wait);

    }

    @Test
    public void successfully_setting_new_password() {
        profile_button(wait);

        WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        settings.click();

        WebElement change_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ID>\")]")));
        change_password.click();

        WebElement old_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[3]/android.widget.EditText")));
        old_password.click();
        old_password.sendKeys("@Password123");

        driver.hideKeyboard();

        WebElement new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[6]/android.widget.EditText")));
        new_password.click();
        new_password.sendKeys("@Password1234");

        driver.hideKeyboard();

        WebElement confirm_new_password = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[13]/android.widget.EditText")));
        confirm_new_password.click();
        confirm_new_password.sendKeys("@Password1234");

        driver.hideKeyboard();

        WebElement confirm_edits = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm_edits.click();

        WebElement otp = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText")));
        otp.click();
        otp.sendKeys("000000");

        confirm_button(wait);

    }

    @Test
    public void successfully_setting_new_pin() throws InterruptedException {
        profile_button(wait);

        WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        settings.click();

        WebElement change_pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        change_pin.click();

        for (int i = 0; i < 6; i++) {
            WebElement old_pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            old_pin.click();
        }

        Thread.sleep(500);

        for (int i = 1; i <= 6; i++) {
            WebElement new_pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(String.valueOf(i))));
            new_pin.click();
        }


    }

    @Test
    public void successfully_change_language() {
        profile_button(wait);

        WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        settings.click();

        WebElement language = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"Language\")]")));
        language.click();

        WebElement thai = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        thai.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button.click();

        WebElement language1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"ภาษา\")]")));
        language1.click();

        WebElement english = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        english.click();

        WebElement back_button1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button1.click();

        WebElement back_button2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button2.click();

    }

    @Test
    public void successfully_change_theme() {
        profile_button(wait);

        WebElement settings = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        settings.click();

        for (int i = 0; i < 2; i++) {
            WebElement theme = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Switch\").instance(2)")));
            theme.click();
        }

        WebElement back_button2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button2.click();
    }

    @Test
    public void successfully_click_term_and_policy() {

        profile_button(wait);

        WebElement terms_and_policy = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        terms_and_policy.click();

        WebElement terms_and_conditions = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        terms_and_conditions.click();

        close_button(wait);

        WebElement privacy_policy = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        privacy_policy.click();

        close_button(wait);

        WebElement disclosing_information = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        disclosing_information.click();

        close_button(wait);

        WebElement complaint_channel = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        complaint_channel.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button.click();

        WebElement right_request_form = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        right_request_form.click();

        WebElement back_button1 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button1.click();

        WebElement back_button2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button")));
        back_button2.click();

    }

    @Test
    public void successfully_fill_in_complaint_channel_form() {
        profile_button(wait);

        WebElement terms_and_policy = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        terms_and_policy.click();

        WebElement complaint_channel = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        complaint_channel.click();

        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText[1]")));
        email.click();
        email.sendKeys("testuser4@example.com");

        driver.hideKeyboard();

        WebElement phone_number = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText[2]")));
        phone_number.click();
        phone_number.sendKeys("088888888");

        driver.hideKeyboard();

        WebElement report = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText[3]")));
        report.click();
        report.sendKeys("report");

        driver.hideKeyboard();

        WebElement request = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText[4]")));
        request.click();
        request.sendKeys("request");

        driver.hideKeyboard();

        WebElement attachment = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        attachment.click();

        WebElement image = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        image.click();

        WebElement photo = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ImageView[@resource-id=\"com.google.android.providers.media.module:id/icon_thumbnail\"]")));
        photo.click();

        WebElement send = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        send.click();

        confirm_button(wait);

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
