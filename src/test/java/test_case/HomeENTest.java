package test_case;

import static helper.ButtonHelper.back_button1;
import static helper.ButtonHelper.back_button_to_invest;
import static helper.ButtonHelper.home_button;
import static helper.DeviceHelper.handle_hidden_element_click;
import static helper.DeviceHelper.handle_hidden_element_without_click;
import static helper.DeviceHelper.login;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;
import static helper.DeviceHelper.perform_click_view_all_button;
import static helper.FileReaderHelper.read_test_file_for_login_of_one_user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import config.AppiumConfig;
import data.Login;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class HomeENTest {
    private static final Logger log = LoggerFactory.getLogger(HomeENTest.class);
    private static AndroidDriver driver;
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
    public void successfully_click_all_menus_on_the_home_page() {

        home_button(wait);

        WebElement invest = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        invest.click();

//        back_button1(wait);
        home_button(wait);

        WebElement exclusive = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        exclusive.click();

        back_button1(wait);

        WebElement compare = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        compare.click();

        back_button1(wait);

        WebElement contact = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        contact.click();

        WebElement back_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        back_button.click();

        WebElement articles = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        articles.click();

        back_button1(wait);

    }

    @Test
    public void successfully_select_fund_on_the_home_page() {

        home_button(wait);

        WebElement fund_element = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, \"<ID>\")]")));
        fund_element.click();

        back_button_to_invest(wait);
    }

    @Test
    public void successfully_click_view_all_to_see_all_funds() {

        home_button(wait);

        By strategies = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_without_click(driver, wait, strategies);

        By view_all = AppiumBy.accessibilityId("<ID>");

        perform_click_view_all_button(view_all, driver);

        home_button(wait);

    }

    @Test
    public void successfully_click_view_all_to_see_all_articles() {

        home_button(wait);

        By articles = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_click(driver, wait, articles);

        By view_all = AppiumBy.accessibilityId("<ID>");

        perform_click_view_all_button(view_all, driver);

        back_button1(wait);

    }


    @Test
    public void successfully_click_view_all_to_see_all_activity_calendar() {

        home_button(wait);

        By activity_calendar = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_click(driver, wait, activity_calendar);

        By view_all = AppiumBy.androidUIAutomator("new UiSelector().description(\"<ID>\").instance(0)");

        perform_click_view_all_button(view_all, driver);

        back_button1(wait);

    }

    @Test
    public void successfully_click_view_all_to_see_all_crypto_news_around_the_world() {

        home_button(wait);

        By crypto_news = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_without_click(driver, wait, crypto_news);

        By view_all = AppiumBy.androidUIAutomator("new UiSelector().description(\"<ID>\").instance(1)");

        perform_click_view_all_button(view_all, driver);

        back_button1(wait);

    }

    @Test
    public void successfully_click_view_all_crypto_coffee() {

        home_button(wait);

        By crypto_news = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_without_click(driver, wait, crypto_news);

        By view_all = AppiumBy.androidUIAutomator("new UiSelector().description(\"<ID>\").instance(0)");

        perform_click_view_all_button(view_all, driver);

        back_button1(wait);

    }

    @Test
    public void successfully_click_notification_button() {

        home_button(wait);
        WebElement notification = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)")));
        notification.click();

        back_button1(wait);
    }

    @Test
    public void successfully_click_profile_button() {
        home_button(wait);

        WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")));
        profile.click();

        back_button1(wait);
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
