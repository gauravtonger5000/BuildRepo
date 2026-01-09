package OLX;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class driverClass {

    // WebDriver instance reference (can be used if you want to store driver in class level)
    WebDriver driver;

    // Constructor to receive driver instance if needed
    public driverClass(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * This method is responsible for:
     * 1. Selecting browser based on system property
     * 2. Configuring ChromeOptions
     * 3. Initializing and returning WebDriver instance
     *
     * @return WebDriver instance
     */
    public static WebDriver browserSel() throws EncryptedDocumentException, InterruptedException {

        // Read browser name from JVM argument
        // Example: -Dbrowser="Web Chrome"
        // If not provided, default will be "Web Chrome"
        String browserName = System.getProperty("browser", "Web Chrome");

        WebDriver driver = null;

        switch (browserName) {

        case "Web Chrome": {

            // Get user home directory (e.g., C:\Users\Username)
            String userHome = System.getProperty("user.home");

            // Build ChromeDriver path dynamically
            // Example: C:\Users\Username\Downloads\chromedriver.exe
            String chromeDriverPath = userHome + File.separator + "Downloads"
                    + File.separator + "chromedriver.exe";

            File chromeDriverFile = new File(chromeDriverPath);

            // Validate ChromeDriver existence
            if (!chromeDriverFile.exists()) {
                throw new RuntimeException("ChromeDriver not found at: " + chromeDriverPath);
            }

            // Set ChromeDriver executable path
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);

            // Create ChromeOptions object to customize browser behavior
            ChromeOptions options = new ChromeOptions();

            // ---------------- BASIC REQUIRED OPTIONS ----------------

            // Disable browser notifications (important for OLX)
            options.addArguments("--disable-notifications");

            // Disable Chrome infobars ("Chrome is being controlled by automated test software")
            options.addArguments("--disable-infobars");

            // Disable automation controlled flag (helps reduce bot detection) and able to Log in using Automation
            
            options.addArguments("--disable-blink-features=AutomationControlled");

            // ---------------- VERY IMPORTANT ----------------

            // Fix issue related to Chrome 111+ versions
            options.addArguments("--remote-allow-origins=*");


            // DO NOT use driver.manage().window().maximize() It may cause crash or white screen in some systems
            options.addArguments("--start-maximized");

            Map<String, Object> prefs = new HashMap<>();

//             NOTE: This will also block image upload functionality
            // prefs.put("profile.managed_default_content_settings.images", 2);

            // Apply preferences to ChromeOptions
            options.setExperimentalOption("prefs", prefs);

            // Initialize ChromeDriver with configured options
            driver = new ChromeDriver(options);
        }
            break;

        default:
            throw new IllegalStateException("Unexpected browser value: " + browserName);
        }

        // Return initialized WebDriver
        return driver;
    }
}
