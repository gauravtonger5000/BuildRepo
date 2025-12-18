package CarTrade;

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
			String downloadsFolderPath = userHome + File.separator + "Downloads";
			String chromeDriverPath = downloadsFolderPath + File.separator + "chromedriver.exe";
			File chromeDriverFile = new File(chromeDriverPath);
			if (!chromeDriverFile.exists()) {
				throw new RuntimeException("ChromeDriver not found in Downloads folder: " + chromeDriverPath);
			}
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
//			Map<String, Object> prefs = new HashMap<>();
//			prefs.put("credentials_enable_service", false); // Disable popup
//			prefs.put("profile.password_manager_enabled", false); // Disable password manager

			
//			USE it where application add validation to not logged in using automation
//			options.setExperimentalOption("prefs", prefs);
//			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//			options.setExperimentalOption("useAutomationExtension", false);
//			options.addArguments("--disable-blink-features=AutomationControlled");
			
//				options.addArguments("--headless=new");
//			options.addArguments("--disable-gpu");
//			options.addArguments("--no-sandbox");
//			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-notifications");
//			options.addArguments("--window-size=1366,768");  // IMPORTANT: This replaces maximize

			driver = new ChromeDriver(options);
//			((JavascriptExecutor) driver).executeScript(
//			        "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
//			);

			// Window maximize (avoid headless)
			driver.manage().window().maximize();
		}
			break;
		
		default:
			throw new IllegalStateException("Unexpected browser: " + browserName);
		}
		return driver;
	}
}
