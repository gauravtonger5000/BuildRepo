package OLX;

import java.io.File;
import java.util.Arrays;
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
    public static WebDriver browserSel() {

        String browserName = System.getProperty("browser", "Web Chrome");

        if (!"Web Chrome".equals(browserName)) {
            throw new IllegalStateException("Unsupported browser: " + browserName);
        }

        String userHome = System.getProperty("user.home");
        String chromeDriverPath = userHome + File.separator + "Downloads" 
                                + File.separator + "chromedriver.exe";

        if (!new File(chromeDriverPath).exists()) {
            throw new RuntimeException("ChromeDriver not found at: " + chromeDriverPath);
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();

        // Stealth options
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Realistic User-Agent
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");

        // === Profile Configuration (Choose ONE) ===
        
        // Option A: Use a separate profile (Recommended)
        String userDataDir = userHome + "\\AppData\\Local\\Google\\Chrome\\User Data";
        options.addArguments("user-data-dir=" + userDataDir);
        options.addArguments("profile-directory=Profile 1");   // Change to your profile name

        // Option B: No custom profile (for testing)
        // Do not add user-data-dir / profile-directory

        WebDriver driver = new ChromeDriver(options);

        // Hide automation flags
        ((ChromeDriver) driver).executeScript(
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});"
        );

        return driver;
    }
}
