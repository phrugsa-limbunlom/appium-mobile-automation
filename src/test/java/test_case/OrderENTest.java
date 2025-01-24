package test_case;

import static helper.ButtonHelper.back_button;
import static helper.ButtonHelper.back_button_to_invest;
import static helper.ButtonHelper.close_button;
import static helper.ButtonHelper.confirm_button;
import static helper.ButtonHelper.history_button;
import static helper.ButtonHelper.invest_button;
import static helper.DeviceHelper.*;
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
import java.util.List;

import config.AppiumConfig;
import data.Login;
import helper.ButtonHelper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;


public class OrderENTest {
    private static final Logger log = LoggerFactory.getLogger(OrderENTest.class);
    private static AndroidDriver driver;
    private static WebDriverWait wait;

    private static final List<String> funds = List.of("M-A",
            "M-ACT",
            "M-PT",
            "M-TRIGGER3",
            "M-TRIGGER2",
            "M-NEXT",
            "M-TT",
            "M-APP-A",
            "M-MODULAR",
            "T-EARMARK",
            "E-perfFeeTest2",
            "M-WL-1",
            "M-BTCA",
            "M-META");


    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {

        // Read test data from file
        Login loginData = read_test_file_for_login_of_one_user();

        UiAutomator2Options options = AppiumConfig.getOptions();

        options.setCapability("unicodeKeyboard", true);
        options.setCapability("resetKeyboard", true);


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
    public void successfully_click_all_timeframe_selector() {

        List<String> timeframe_list = List.of("1D", "1W", "3M", "6M", "1Y", "YTD", "MAX");

        invest_button(wait);

        for (String fund : funds) {

            log.info("Fund: " + fund);

            if (!fund.equals("<ID1>") && !fund.equals("<ID2>") && !fund.equals("<ID3>")) {

                By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", fund));
                handle_hidden_element_without_click(driver, wait, fund_element);

                for (String timeframe : timeframe_list) {
                    By timeframe_element = AppiumBy.androidUIAutomator(String.format("new UiSelector().description(\"%s\").instance(1)", timeframe));
                    wait.until(ExpectedConditions.elementToBeClickable(timeframe_element)).click();
                }
            }
        }
    }

    @Test
    public void all_funds_are_displayed_on_the_invest_page() {

        for (String fund : funds) {
            invest_button(wait);

            log.info("Fund: " + fund);

            By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", fund));
            handle_hidden_element_click(driver, wait, fund_element);

            By overview = AppiumBy.accessibilityId("<ID>");
            By proportion = AppiumBy.accessibilityId("<ID>");
            By risk = AppiumBy.accessibilityId("<ID>");
            By fee = AppiumBy.accessibilityId("<ID>");

            wait.until(ExpectedConditions.elementToBeClickable(overview)).click();
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(proportion)).click();
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(risk)).click();
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(fee)).click();
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(overview)).click();
            invest_button(wait);
            back_button(wait);
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(proportion)).click();
            invest_button(wait);
            back_button(wait);
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(risk)).click();
            invest_button(wait);
            back_button(wait);
            close_button(wait);

            wait.until(ExpectedConditions.elementToBeClickable(fee)).click();
            invest_button(wait);
            back_button(wait);
            close_button(wait);

            invest_button(wait);
            back_button(wait);
            back_button_to_invest(wait);
        }
    }

    @Test
    public void successfully_invest_fund_with_download_QR_code() {
        invest_button(wait);

        By fund = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", funds.get(0)));
        handle_hidden_element_click(driver, wait, fund);

        invest_button(wait);

        WebElement strategic_capital = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        strategic_capital.click();

        confirm_button(wait);

        confirm_button(wait);

        for (int i = 0; i < 6; i++) {
            WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            pin.click();
        }

        WebElement download_QR = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        download_QR.click();

        close_button(wait);

    }

    @Test
    public void successfully_invest_fund_with_confirm_QR_code() {
        invest_button(wait);

        By fund = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", funds.get(0)));
        handle_hidden_element_click(driver, wait, fund);

        invest_button(wait);

        WebElement strategic_capital = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        strategic_capital.click();

        confirm_button(wait);

        confirm_button(wait);

        for (int i = 0; i < 6; i++) {
            WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            pin.click();
        }

        WebElement confirm_QR = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm_QR.click();

        // can see order in order history

        By orderElementLocator = By.xpath("//android.widget.ImageView[contains(@content-desc, '<ID>')]");
        WebElement orderElement = driver.findElement(orderElementLocator);


        String contentDesc = orderElement.getAttribute("content-desc");
        String orderIdPrefix = "Order Id : ";

        int startIndex = contentDesc.indexOf(orderIdPrefix) + orderIdPrefix.length();
        int endIndex = contentDesc.indexOf("\n", startIndex);

        String orderId = contentDesc.substring(startIndex, endIndex).trim();

        log.info("Order ID: " + orderId);

        back_button(wait);

        By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", orderId));
        handle_hidden_element_click(driver, wait, fund_element);

        back_button(wait);

    }

    @Test
    public void successfully_invest_fund_with_transfer_account() {
        invest_button(wait);

        By fund = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", funds.get(0)));
        handle_hidden_element_click(driver, wait, fund);

        invest_button(wait);

        WebElement strategic_capital = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        strategic_capital.click();

        WebElement payment_method = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        payment_method.click();

        By slip_attach = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, slip_attach);

        WebElement upload_image = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        upload_image.click();

        WebElement image = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.google.android.providers.media.module:id/icon_thumbnail\").instance(0)")));
        image.click();

        By confirm = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, confirm);

        confirm_button(wait);

        for (int i = 0; i < 6; i++) {
            WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            pin.click();
        }

        // can see order in order history

        By orderElementLocator = By.xpath("//android.view.View[contains(@content-desc, '<ID>')]");
        WebElement orderElement = driver.findElement(orderElementLocator);


        String contentDesc = orderElement.getAttribute("content-desc");
        String orderIdPrefix = "Order Id : ";

        int startIndex = contentDesc.indexOf(orderIdPrefix) + orderIdPrefix.length();
        int endIndex = contentDesc.indexOf("\n", startIndex);

        String orderId = contentDesc.substring(startIndex, endIndex).trim();

        log.info("Order ID: " + orderId);

        back_button(wait);

        By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", orderId));
        handle_hidden_element_click(driver, wait, fund_element);

        back_button(wait);

    }

    @Test
    public void successfully_click_order_ID_from_order_history() {

        history_button(wait);

        String orderId = "8740";

        By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", orderId));
        handle_hidden_element_click(driver, wait, fund_element);

        back_button(wait);
    }

    @Test
    public void successfully_find_order_ID_from_search_button() {

        ButtonHelper.history_button(wait);

        String orderId = "8740";

        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc=\"List of buying/selling/switching funds\"]/android.widget.ImageView[2]")));
        search.click();
        search.sendKeys(orderId);

        By fund_element = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", orderId));
        handle_hidden_element_click(driver, wait, fund_element);
        ButtonHelper.back_button(wait);
    }


    @Test
    public void successfully_invest_fund_with_input_capital() throws InterruptedException {
        invest_button(wait);

        By fund = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", funds.get(0)));
        handle_hidden_element_click(driver, wait, fund);

        invest_button(wait);

        WebElement strategic_capital = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[@text=\"0.00\"]")));
        strategic_capital.click();
        strategic_capital.click();
        Thread.sleep(500);
        strategic_capital.sendKeys("1");
        strategic_capital.sendKeys("15000");
        WebElement payment_method = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        payment_method.click();

        By slip_attach = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, slip_attach);

        WebElement upload_image = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        upload_image.click();

        WebElement image = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.google.android.providers.media.module:id/icon_thumbnail\").instance(0)")));
        image.click();

        By confirm = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, confirm);

        confirm_button(wait);

        for (int i = 0; i < 6; i++) {
            WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            pin.click();
        }

    }

    @Test
    public void failed_invest_fund_with_less_than_minimum_capital() throws InterruptedException {

        invest_button(wait);

        By fund = AppiumBy.xpath(String.format("//*[contains(@content-desc, \"%s\")]", funds.get(0)));
        handle_hidden_element_click(driver, wait, fund);

        invest_button(wait);

        WebElement strategic_capital = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText[@text=\"0.00\"]")));
        strategic_capital.click();
        strategic_capital.click();
        Thread.sleep(500);
        strategic_capital.sendKeys("1");
        strategic_capital.sendKeys("1");

        confirm_button(wait);
        confirm_button(wait);

        for (int i = 0; i < 6; i++) {
            WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            pin.click();
        }

        WebElement failed = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        failed.click();

    }

    @AfterEach
    public void tearDown() {
        logout(driver, wait);
        if (driver != null) {
            driver.quit();
        }
    }
}
