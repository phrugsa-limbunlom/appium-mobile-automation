package helper;

import static helper.FileNameHelper.FILE_NAME_REGISTER;
import static helper.FileNameHelper.FILE_NAME_LOGIN;
import static helper.FileNameHelper.FILE_NAME_LOGIN_ONE_USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Login;
import data.Registration;

public class FileReaderHelper {

    private static final Logger log = LoggerFactory.getLogger(FileReaderHelper.class);

    public static List<Registration> read_test_file_for_registration() {
        List<Registration> registrationList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_REGISTER))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split by pipe (|)
                if (parts.length == 3) {
                    String email = parts[0];
                    String password = parts[1];
                    String agentcode = parts[2];

                    log.info("Email: " + email);
                    log.info("Password: " + password);
                    log.info("Agentcode:" + agentcode);

                    Registration registrationData = new Registration(email, password, agentcode);
                    registrationList.add(registrationData);
                } else {
                    log.info("Invalid line format: " + line);
                }
            }

        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
        }
        return registrationList;
    }

    public static List<Login> read_test_file_for_login() {
        List<Login> loginList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_LOGIN))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split by pipe (|)
                if (parts.length == 2) {
                    String email = parts[0];
                    String password = parts[1];

                    log.info("Email: " + email);
                    log.info("Password: " + password);

                    Login loginData = new Login(email, password);
                    loginList.add(loginData);
                } else {
                    log.info("Invalid line format: " + line);
                }
            }

        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
        }
        return loginList;
    }

    public static Login read_test_file_for_login_of_one_user() {
        Login loginData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_LOGIN_ONE_USER))) {
            reader.readLine(); // Skip header
            String line = reader.readLine();
            String[] parts = line.split("\\|"); // Split by pipe (|)
            if (parts.length == 2) {
                String email = parts[0];
                String password = parts[1];

                log.info("Email: " + email);
                log.info("Password: " + password);

                loginData = new Login(email, password);
            } else {
                log.info("Invalid line format: " + line);
            }
        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
        }
        return loginData;
    }
}
