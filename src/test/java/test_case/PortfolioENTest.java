package test_case;

import static helper.ButtonHelper.portfolio_button;
import static helper.DeviceHelper.login;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import config.AppiumConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class PortfolioENTest {

    private AndroidDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {

        UiAutomator2Options options = AppiumConfig.getOptions();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        // Set implicit wait timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        // Set explicit wait timeout
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        pass_the_welcome_page_with_English_language(wait);

        Thread.sleep(500);

        String email = "email";
        String password = "password";

        login(driver, wait, email, password);
    }


    @Test
    public void successfully_click_funds_in_portfolio() {

        portfolio_button(wait);

        WebElement funds = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        funds.click();

    }

    @Test
    public void successfully_click_deposit_of_the_fund() {
        portfolio_button(wait);

        WebElement fund = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, '<ID>')]")));
        fund.click();

        WebElement details = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        details.click();
    }

    @Test
    public void successfully_click_switching_of_the_fund() {
        portfolio_button(wait);

        WebElement fund = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, '<ID>')]")));
        fund.click();

        WebElement details = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        details.click();
    }

    @Test
    public void successfully_click_withdraw_of_the_fund() {
        portfolio_button(wait);

        WebElement fund = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, '<ID>')]")));
        fund.click();

        WebElement details = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        details.click();
    }

    @Test
    public void successfully_click_details_of_the_fund() {
        portfolio_button(wait);

        WebElement fund = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, '<ID>')]")));
        fund.click();

        WebElement details = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        details.click();
    }

    @Test
    public void successfully_click_proportion_in_portfolio() {

        portfolio_button(wait);

        WebElement proportion = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        proportion.click();

    }

    @Test
    public void successfully_click_invest_value_in_portfolio() {

        portfolio_button(wait);

        WebElement invest_value = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        invest_value.click();

        List<String> investment_years = List.of("1M", "3M", "6M", "1Y", "MAX");

        for (String investment_year : investment_years) {
            WebElement investment_year_element = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(investment_year)));
            investment_year_element.click();
        }

    }

    @Test
    public void successfully_click_all_ROI_chart_in_portfolio() {

        portfolio_button(wait);

        WebElement roi_chart = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        roi_chart.click();

        List<String> investment_years = List.of("1M", "3M", "6M", "1Y", "MAX");

        for (String investment_year : investment_years) {
            WebElement investment_year_element = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(investment_year)));
            investment_year_element.click();
        }

    }

    @Test
    public void successfully_export_performance_report() {

        portfolio_button(wait);

        WebElement funds = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        funds.click();

        WebElement performance_report = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc, '<ID>')]")));
        performance_report.click();

        List<String> months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        for (String month : months) {
            WebElement month_element = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(month)));
            month_element.click();
        }

        WebElement next_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_button.click();

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
