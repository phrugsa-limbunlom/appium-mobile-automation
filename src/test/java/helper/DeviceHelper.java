package helper;

import static helper.ButtonHelper.profile_button;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class DeviceHelper {

    private static void perform_click_on_element(By locator, AppiumDriver driver, String side, Double width_percentage) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // Get the element's rectangle (bounds)
        Rectangle bounds = element.getRect();

        int buttonWidth = (int) (bounds.width * width_percentage);
        int x = 0;
        int y = 0;

        if (Objects.equals(side, "Left")) {
            x = bounds.x + (buttonWidth / 2);
            y = bounds.y + (bounds.height / 2);
        } else if (Objects.equals(side, "Right")) {
            x = bounds.x + bounds.width - (buttonWidth / 2);
            y = bounds.y + (bounds.height / 2);
        }

        // Create a PointerInput object for touch events
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the action sequence
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(100)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the action
        driver.perform(Collections.singletonList(sequence));

    }

    public static void perform_click_view_all_button(By locator, AppiumDriver driver) {
        perform_click_on_element(locator, driver, "Right", 0.3);
    }

    public static void perform_click_sign_up_button(By locator, AppiumDriver driver) {
        perform_click_on_element(locator, driver, "Right", 0.7);
    }

    public static void perform_click_on_forget_password_button(By locator, AppiumDriver driver) {
        perform_click_on_element(locator, driver, "Right", 0.2);
    }

    public static void perform_click_on_checkbox(By locator, AppiumDriver driver) {
        perform_click_on_element(locator, driver, "Left", 0.2);
    }

    private static void scrollDown(AppiumDriver driver) {
        int startX = 500;  // Set start point X coordinate
        int startY = 300;  // Set start point Y coordinate (near the top)
        int endX = 500;  // Set end point X coordinate (same as start to maintain horizontal position)
        int endY = 500;  // Set end point Y coordinate (towards the bottom)

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scrollDown = new Sequence(finger, 1);
        scrollDown.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        scrollDown.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scrollDown.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        scrollDown.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(scrollDown));
    }

    private static void scrollUp(AppiumDriver driver) {

        int startX = 500;  // Set start point X coordinate
        int startY = 500;  // Set start point Y coordinate
        int endX = 500;  // Set end point X coordinate (same as start to maintain horizontal position)
        int endY = 300;  // Set end point Y coordinate

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scrollUp = new Sequence(finger, 1);
        scrollUp.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        scrollUp.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scrollUp.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        scrollUp.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(scrollUp));
    }

    private static boolean scrollToElementUntilDisplayed(AppiumDriver driver, WebDriverWait wait, By elementLocator, int maxScrollAttempts, String scrollMethod) {
        int attempts = 0;
        boolean isDisplayed = false;

        while (!isDisplayed && attempts < maxScrollAttempts) {
            WebElement element = null;
            try {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            } catch (Exception e) {
                if (scrollMethod.equals("down")) {
                    scrollDown(driver);
                } else if (scrollMethod.equals("up")) {
                    scrollUp(driver);
                }
                attempts++;
            }

            isDisplayed = element != null && element.isDisplayed();

            if (!isDisplayed && attempts == maxScrollAttempts - 1) {
                attempts = 0;
            }
        }

        return isDisplayed;
    }

    public static void validate_invalid_information(By elementLocator, AppiumDriver driver, WebDriverWait wait) {
        boolean isElementDisplayed = scrollToElementUntilDisplayed(driver, wait, elementLocator, 5, "down");
        if (isElementDisplayed) {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            Assertions.assertTrue(element.isDisplayed(), "Element should be visible");
        }
    }

    public static void handle_hidden_element_click(AppiumDriver driver, WebDriverWait wait, By elementLocator) {
        boolean isElementDisplayed = scrollToElementUntilDisplayed(driver, wait, elementLocator, 5, "up");
        if (isElementDisplayed) {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            element.click();
        }
    }

    public static void handle_hidden_element_without_click(AppiumDriver driver, WebDriverWait wait, By elementLocator) {
        boolean isElementDisplayed = scrollToElementUntilDisplayed(driver, wait, elementLocator, 5, "up");
        if (isElementDisplayed) {
            wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        }
    }

    public static void handle_hidden_element_fill_form(AppiumDriver driver, WebDriverWait wait, By elementLocator, String message) {
        boolean isElementDisplayed = scrollToElementUntilDisplayed(driver, wait, elementLocator, 3, "up");
        if (isElementDisplayed) {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            element.click();
            element.sendKeys(message);
            ((AndroidDriver) driver).hideKeyboard();
        }
    }

    public static void perform_click_on_checkbox(AppiumDriver driver, WebDriverWait wait, By locator) {

        boolean isElementDisplayed = scrollToElementUntilDisplayed(driver, wait, locator, 3, "up");

        if (isElementDisplayed) {

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            Rectangle bounds = element.getRect();

            // Assuming the checkbox is on the left side, taking up about 20% of the width
            int checkboxWidth = (int) (bounds.width * 0.2);
            int x = bounds.x + (checkboxWidth / 2);
            int y = bounds.y + (bounds.height / 2);

            // Perform the click action
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence sequence = new Sequence(finger, 1)
                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(new Pause(finger, Duration.ofMillis(100)))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(sequence));
        }
    }

    public static Process run_shell_script_to_trigger_webhook(String scriptPath, String slug_number) throws IOException {

        String commandString = scriptPath + " " + slug_number;

        String[] command = {"wsl", "-d", "Ubuntu", "bash", "-c", commandString};

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        return processBuilder.start();
    }

    public static StringBuilder extract_api_response_from_script(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        return output;
    }

    private static String reconstruct_password(String password) {
        return "*".repeat(password.length());
    }

    public static void login(AppiumDriver driver, WebDriverWait wait, String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")));
        emailField.click();
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        passwordField.click();
        passwordField.sendKeys(password);

        String encoded_password = reconstruct_password(password);

        System.out.println("encoded_password " + encoded_password);

        WebElement editTextElement = driver.findElement(By.xpath(String.format("//android.widget.EditText[@text=\"%s\"]", encoded_password)));
        String editTextValue = editTextElement.getAttribute("text");

        if (editTextValue.equals(encoded_password)) {
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("<ID>")));
            loginButton.click();
        }

        WebElement pin_code = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(12)")));

        if (pin_code.isDisplayed()) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("0")));
                    pin.click();
                }
            }
        }
    }

    public static void login_TH(AppiumDriver driver, WebDriverWait wait, String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")));
        emailField.click();
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)")));
        passwordField.click();
        passwordField.sendKeys(password);

        String encoded_password = reconstruct_password(password);

        System.out.println("encoded_password " + encoded_password);

        WebElement editTextElement = driver.findElement(By.xpath(String.format("//android.widget.EditText[@text=\"%s\"]", encoded_password)));
        String editTextValue = editTextElement.getAttribute("text");

        if (editTextValue.equals(encoded_password)) {
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().description(\"เข้าสู่ระบบ\").instance(1)")));
            loginButton.click();
        }

        WebElement pin_code = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(12)")));

        if (pin_code.isDisplayed()) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    WebElement pin = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("0")));
                    pin.click();
                }
            }
        }
    }

    public static void logout(AppiumDriver driver, WebDriverWait wait) {
        profile_button(wait);

        By logout = AppiumBy.accessibilityId("<ID>");

        handle_hidden_element_click(driver, wait, logout);

        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
    }

    public static void logout_TH(AppiumDriver driver, WebDriverWait wait) {
        ButtonTHHelper.profile_button(wait);

        By logout = AppiumBy.accessibilityId("<ไอดี>");

        handle_hidden_element_click(driver, wait, logout);

        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
    }

    public static void pass_the_welcome_page_with_English_language(WebDriverWait wait) {

        WebElement allow_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button")));
        allow_button.click();

        WebElement element_lan = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ID>")
        ));

        element_lan.click();

        WebElement element_start = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ID>")
        ));

        element_start.click();
        for (int i = 0; i < 2; i++) {
            WebElement skip_button = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("<ID>")
            ));
            skip_button.click();
        }

        WebElement start_with_login = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ID>")
        ));
        start_with_login.click();
    }

    public static void pass_the_welcome_page_with_Thai_language(WebDriverWait wait) {
        WebElement allow_button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button")));
        allow_button.click();

        WebElement element_lan = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ไอดี>")
        ));

        element_lan.click();

        WebElement element_start = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ไอดี>")
        ));

        element_start.click();

        for (int i = 0; i < 2; i++) {
            WebElement skip_button = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("<ไอดี>")
            ));
            skip_button.click();
        }
        // Start with Login
        WebElement start_with_login = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId("<ไอดี>")
        ));
        start_with_login.click();
    }
}
