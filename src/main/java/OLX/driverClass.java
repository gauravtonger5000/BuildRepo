package OLX;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class driverClass

{
	WebDriver driver;

	public driverClass(WebDriver driver) {
		this.driver = driver;
	}

	public static WebDriver browserSel() throws EncryptedDocumentException, InterruptedException {
		String browserName = System.getProperty("browser", "Web Chrome");
		WebDriver driver = null;
		switch (browserName) {
		case "Web Chrome": {
			String userHome = System.getProperty("user.home");
			String chromeDriverPath = userHome + File.separator + "Downloads"
			        + File.separator + "chromedriver.exe";

			File chromeDriverFile = new File(chromeDriverPath);
			if (!chromeDriverFile.exists()) {
			    throw new RuntimeException("ChromeDriver not found: " + chromeDriverPath);
			}

			System.setProperty("webdriver.chrome.driver", chromeDriverPath);

			ChromeOptions options = new ChromeOptions();

			// ---------- REQUIRED ----------
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-blink-features=AutomationControlled");

			// ---------- VERY IMPORTANT ----------
			options.addArguments("--remote-allow-origins=*");

			// ---------- AVOID CRASH ----------
			options.addArguments("--start-maximized"); // ❌ DO NOT use driver.manage().window().maximize()

			// ---------- Disable password popup ----------
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			// ❌ REMOVE THESE (CAUSE UNKNOWN ERROR)
			// options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
			// options.setExperimentalOption("useAutomationExtension", false);

			driver = new ChromeDriver(options);

		}
			break;
		
		default:
			throw new IllegalStateException("Unexpected browser: " + browserName);
		}
		return driver;
	}
}
