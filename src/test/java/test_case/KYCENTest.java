package test_case;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import config.AppiumConfig;
import data.Registration;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static helper.DeviceHelper.extract_api_response_from_script;
import static helper.DeviceHelper.handle_hidden_element_click;
import static helper.DeviceHelper.handle_hidden_element_fill_form;
import static helper.DeviceHelper.login;
import static helper.DeviceHelper.pass_the_welcome_page_with_English_language;
import static helper.DeviceHelper.perform_click_on_checkbox;
import static helper.DeviceHelper.run_shell_script_to_trigger_webhook;
import static helper.FileReaderHelper.read_test_file_for_registration;


public class KYCENTest {
    private static final Logger log = LoggerFactory.getLogger(KYCENTest.class);
    private AndroidDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException {

        // Read test data from file
        List<Registration> registrationList = read_test_file_for_registration();

        UiAutomator2Options options = AppiumConfig.getOptions();

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        // Set implicit wait timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        // Set explicit wait timeout
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        pass_the_welcome_page_with_English_language(wait);

        login(driver, wait, registrationList.get(0).getEmail(), registrationList.get(0).getPassword());

        WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        banner.click();

    }

    @Test
    public void successfully_pass_the_KYC_step_2() throws IOException, InterruptedException {
        // Chrome Driver
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-first-run");

        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setNoReset(true)
                .setAdbExecTimeout(Duration.ofSeconds(60));

        options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Set implicit wait timeout to 30 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

        driver.get("<url>");

        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.contains("WEBVIEW_chrome")) {
                driver.context(contextName);
                break;
            }
        }

        WebElement accept_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div/div/div/div/div/div[2]/div/button[1]/span")));
        accept_button.click();

        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[4]/div[2]/div/div/form/div[1]/div[2]/input")));
        email.click();
        email.sendKeys("testuser4@example.com");

        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[4]/div[2]/div/div/form/div[2]/div[2]/div/input")));
        password.click();
        password.sendKeys("@Password123");

        driver.hideKeyboard();

        WebElement login_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[4]/div[2]/div/div/form/button[1]")));
        login_button.click();

        WebElement startButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[7]/div/div/div/div[2]/button[2]")));

        wait.until(ExpectedConditions.elementToBeClickable(startButton));

        startButton.click();

        WebElement live_ness_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[5]/div[2]/div[2]/div[2]/div[2]/div[2]/a/div/div/div[2]/button")));
        live_ness_button.click();

        By linkElement = By.xpath("//*[@id=\"verifioArea\"]/div[3]/button");

        JavascriptExecutor js = driver;

        while (true) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(linkElement));
                element.click();
                break;
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0, -250);");
            }
        }

        // Wait for the new tab to open
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        boolean isPageDisplayed = wait.until(ExpectedConditions.and(
                ExpectedConditions.urlContains("app.uppass.io"),
                webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")
        ));

        String slug_number;

        if (isPageDisplayed) {

            String url = driver.getCurrentUrl();
            log.info("url : {}", url);

            String prefix = "<url>";

            slug_number = url.substring(prefix.length()).split("/")[0];

            log.info("Slug number : {}", slug_number);

            String scriptPath = "./src/test/java/english/script/api_call_webhook.sh";

            Process process = run_shell_script_to_trigger_webhook(scriptPath, slug_number);

            StringBuilder output = extract_api_response_from_script(process);

            int exitCode = process.waitFor();

            assertTrue(output.toString().contains("200"), "Expected status code 200 not found in output");

            assertEquals(0, exitCode, "Script did not execute successfully");

            assertTrue(output.toString().contains("Shell script executed successfully"), "Script success message not found");

            assertFalse(output.toString().contains("Assertion failed"), "Unexpected assertion failure in script output");

        } else {
            log.info("Slug number not found");
        }

    }

    @Test
    public void successfully_fill_DOPA_form_after_fail_identification() {

        WebElement fill_in_dopa_form = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        fill_in_dopa_form.click();

        //DOPA Form
        By identification_number = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(0)");
        handle_hidden_element_fill_form(driver, wait, identification_number, "1111111111111");

        By thai_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(1)");
        handle_hidden_element_fill_form(driver, wait, thai_name, "thai name");

        By thai_middle_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(2)");
        handle_hidden_element_fill_form(driver, wait, thai_middle_name, "thai middle name");

        By thai_last_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(1)");
        handle_hidden_element_fill_form(driver, wait, thai_last_name, "thai lastname");

        By en_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(2)");
        handle_hidden_element_fill_form(driver, wait, en_name, "en name");

        By en_middle_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(3)");
        handle_hidden_element_fill_form(driver, wait, en_middle_name, "en middle name");

        By en_last_name = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(4)");
        handle_hidden_element_fill_form(driver, wait, en_last_name, "en lastname");

        By the_life_time_expiration_date = AppiumBy.accessibilityId("<ID>");

        perform_click_on_checkbox(driver, wait, the_life_time_expiration_date);

        By laser_number = AppiumBy.androidUIAutomator("newUiSelector().className(\"android.widget.EditText\").instance(3)");
        handle_hidden_element_fill_form(driver, wait, laser_number, "ME-1234567-89");

        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        confirm.click();

    }

    @Test
    public void successfully_pass_the_KYC_step_3() {

        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        // Personal Information
        WebElement personal_information = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("<ID>")));

        if (personal_information.isDisplayed()) {

            WebElement nationality_thai = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            nationality_thai.click();

            WebElement nationality_thai_option = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            nationality_thai_option.click();

            By gender = AppiumBy.xpath("//android.widget.ImageView[@content-desc=\"<ID>\"]");
            perform_click_on_checkbox(driver, wait, gender);

            By marital_status = AppiumBy.xpath("//android.widget.ImageView[@content-desc=\"<ID>\"]");
            perform_click_on_checkbox(driver, wait, marital_status);

            By telephone_number = By.xpath("//android.widget.ScrollView/android.widget.EditText[1]");
            handle_hidden_element_fill_form(driver, wait, telephone_number, "0987654321");
        }

        // Address
        By province = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, province);

        By province_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, province_option);

        By district = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, district);

        By district_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, district_option);

        By sub_district = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, sub_district);

        By sub_district_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, sub_district_option);

        // Current Address
        By current_address = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, current_address);

        // Occupation Information
        By occupation_type = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_click(driver, wait, occupation_type);

        By occupation_type_option = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_click(driver, wait, occupation_type_option);

        By name_of_company = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

        handle_hidden_element_fill_form(driver, wait, name_of_company, "company name");

        By the_same_address_check_box = AppiumBy.accessibilityId("<ID>");

        perform_click_on_checkbox(driver, wait, the_same_address_check_box);

        // Income and Investment Information
        By country_of_source_income = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, country_of_source_income);

        By country_of_source_income_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, country_of_source_income_option);

        By asset_type = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, asset_type);

        By average_monthly_income = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, average_monthly_income);

        By average_monthly_income_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, average_monthly_income_option);

        By proportion_of_income = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, proportion_of_income);

        By proportion_of_income_option = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, proportion_of_income_option);

        By beneficiary_declaration = AppiumBy.accessibilityId("<ID>");
        handle_hidden_element_click(driver, wait, beneficiary_declaration);

        WebElement next_page = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page.click();

        // Bank Account
        WebElement bank = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        bank.click();

        WebElement bank_option = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        bank_option.click();

        WebElement bank_account = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.EditText")));
        bank_account.click();
        bank_account.sendKeys("1234567890");

        driver.hideKeyboard();

        WebElement next_page2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page2.click();
    }


    @Test
    public void successfully_pass_the_KYC_step_4() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        // Personal Information
        By age = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, age);

        By education = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, education);

        By financial_burden = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, financial_burden);

        By financial_status = AppiumBy.androidUIAutomator("new UiSelector().description(\"Having less assets than liabilities\").instance(0)");
        perform_click_on_checkbox(driver, wait, financial_status);

        By amount_asset = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, amount_asset);

        By overall_asset = AppiumBy.androidUIAutomator("new UiSelector().description(\"Less than 500,000 baht\").instance(0)");
        perform_click_on_checkbox(driver, wait, overall_asset);

        By digital_asset_value = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, digital_asset_value);

        By digital_asset_proportion = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, digital_asset_proportion);

        // Knowledge experience and Investment goal
        By bank_saving = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, bank_saving);

        By corporate_bond = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, corporate_bond);

        By equity = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, equity);

        By security_experience = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, security_experience);

        By investing_knowledge = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, investing_knowledge);

        By investing_experience = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, investing_experience);

        By investing_risk_appetite = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, investing_risk_appetite);

        By expect_period = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, expect_period);

        // Investment attitude
        By high_chance_return = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, high_chance_return);

        By accept_risk = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, accept_risk);

        By anxious_proportion = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, anxious_proportion);

        By exchange_rate_risk = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, exchange_rate_risk);

        By risk_tolerance = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, risk_tolerance);

        WebElement next_page = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page.click();

        WebElement next_page2 = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page2.click();
    }

    @Test
    public void successfully_pass_the_KYC_step_5() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        By acknowledge = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, acknowledge);

        WebElement next_page = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page.click();

    }

    @Test
    public void successfully_pass_the_KYC_step_6() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next.click();

        WebElement arrow = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)")));
        arrow.click();

        By contract_option1 = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, contract_option1);

        By contract_option2 = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, contract_option2);

        By contract_option3 = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, contract_option3);

        By contract_option4 = AppiumBy.accessibilityId("<ID>");
        perform_click_on_checkbox(driver, wait, contract_option4);

        WebElement next_page = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
        next_page.click();

    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
