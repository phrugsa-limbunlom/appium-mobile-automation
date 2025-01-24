package config;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.FileReader;
import java.time.Duration;

public class AppiumConfig {

    private static final Logger log = LoggerFactory.getLogger(AppiumConfig.class);
    private static final String CONFIG_FILE_PATH = "appium.config.json";

    private static class AppiumConfigData {
        String platformName;
        String deviceName;
        String appPackage;
        String appActivity;
        String automationName;
        boolean noReset;
        long adbExecTimeout;
    }

    public static UiAutomator2Options getOptions() {
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            Gson gson = new Gson();
            AppiumConfigData configData = gson.fromJson(reader, AppiumConfigData.class);

            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName(configData.platformName);
            options.setDeviceName(configData.deviceName);
            options.setAppPackage(configData.appPackage);
            options.setAppActivity(configData.appActivity);
            options.setAutomationName(configData.automationName);
            options.setNoReset(configData.noReset);
            options.setAdbExecTimeout(Duration.ofMillis(configData.adbExecTimeout));

            return options;
        } catch (Exception e) {
            log.error("Error reading Appium configuration file: {}", e.getMessage());
            return null;
        }

    }
}
