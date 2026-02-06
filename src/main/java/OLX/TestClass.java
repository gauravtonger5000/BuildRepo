package OLX;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import groovyjarjarantlr4.v4.codegen.model.Action;

public class TestClass {
	WebDriver driver;

	public TestClass(WebDriver driver) {
		this.driver = driver;
	}

	public void clickLoginButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		By loginBtnLocator = By.xpath("//button[@data-aut-id='btnLogin']");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(loginBtnLocator));
			// Try clicking up to 3 times
			for (int attempt = 1; attempt <= 3; attempt++) {
				try {
					WebElement loginBtn = driver.findElement(loginBtnLocator);
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
							loginBtn);
					Thread.sleep(200);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
//					System.out.println("Click successful");
//					ExtentReportListener.log("Login button clicked successfully", "PASS");
					return; // SUCCESS → exit method

				} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
					System.out.println("Retry due to: " + e.getClass().getSimpleName());
					Thread.sleep(300);
				}
			}
			throw new RuntimeException("Failed to click Login button after 3 attempts.");

		} catch (TimeoutException e) {
			throw new RuntimeException("Waited 60 seconds but Login button is NOT visible.");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}

	public void loginWithMobile() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		try {
			WebElement loginBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-aut-id=\"phoneLogin\"]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
//			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
//			System.out.println("Login With Mobile No. button clicked successfully");
//			ExtentReportListener.log("Login With Mobile No. button clicked successfully", "PASS");
		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 60 Seconds but Login With Mobile element is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterMobileNo(String username) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By enterMob = By.xpath("//input[@placeholder=\"Phone Number\"]");
		try {
			WebElement mobNofield = wait.until(ExpectedConditions.elementToBeClickable(enterMob));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mobNofield);
//			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", mobNofield);
			mobNofield.sendKeys(username);
//			System.out.println("Mobile No. enetered successfully");
//			ExtentReportListener.log("Mobile No. enetered successfully", "PASS");

		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Login With Mobile No Field is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void password(String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By enterPwd = By.xpath("//input[@id=\"password\"]");
		try {
//			WebElement mobNofield = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder=\"Phone Number\"]")));
			WebElement mobNofield = wait.until(ExpectedConditions.elementToBeClickable(enterPwd));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mobNofield);
			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", mobNofield);
			mobNofield.sendKeys(password);
//			System.out.println("Password entered successfully");
//			ExtentReportListener.log("Password entered successfully", "PASS");

		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Password Field is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void nextBtn() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By enterMob = By.xpath("//button[@data-aut-id=\"submitBtn\"]");
		WebElement nextBtnBtn = wait.until(ExpectedConditions.elementToBeClickable(enterMob));

		try {
			for (int i = 1; i <= 3; i++) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextBtnBtn);
					Thread.sleep(500);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtnBtn);
					error();
//					nextBtnBtn.click();
//					System.out.println("Next Button clicked successfully");
//					ExtentReportListener.log("Next Button clicked successfully", "PASS");
					return;
				} catch (org.openqa.selenium.StaleElementReferenceException
						| org.openqa.selenium.ElementClickInterceptedException e) {
					nextBtnBtn.click();
				}
			}
			throw new RuntimeException("Failed to click Next button after 3 attempts.");
		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Next Buttom is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void loginBtn() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By loginBtn = By.xpath("//button[@data-aut-id=\"login-form-submit\"]");

		try {
			WebElement loginBtnElement = wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
			for (int i = 1; i <= 3; i++) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtnElement);
					Thread.sleep(500);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtnElement);
//					nextBtnBtn.click();
//					System.out.println("Login Button clicked successfully");
//					ExtentReportListener.log("Login Button clicked successfully", "PASS");
					return;
				} catch (org.openqa.selenium.StaleElementReferenceException
						| org.openqa.selenium.ElementClickInterceptedException e) {
					loginBtnElement.click();
				}
			}
			throw new RuntimeException("Failed to click Login button after 3 attempts.");
		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Login Buttom is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void error() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		By enterMob = By.xpath("//span[text()=\"Unknown login error\"]");
		try {
			WebElement nextBtnBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(enterMob));
			throw new RuntimeException("Unknown Error has occured");
		} catch (TimeoutException e) {
//			System.out.println("Not get any Error.");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void clickSellButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		By sellButtonLocator = By.xpath("//span[text()='Sell']");

		try {
			WebElement sellBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(sellButtonLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sellBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sellBtn);
//			System.out.println("Sell button clicked successfully.");
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			WebElement sellBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(sellButtonLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sellBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", sellBtn);
//			System.out.println("Sell button clicked successfully.");
		} catch (TimeoutException e) {
			// Show error message using JavaScript Alert
			((JavascriptExecutor) driver).executeScript(
					"alert('Sell button not visible. Please check the element or wait for page load!');");

//			System.err.println("Sell button not clickable within timeout.");
			throw new RuntimeException("Sell Button Click Failed: " + e.getMessage());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void clickCarsButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		By carsButtonLocator = By.xpath("//span[text()='Cars']");

		try {

			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(carsButtonLocator));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

			try {
				element.click(); // Try normal click first
			} catch (ElementClickInterceptedException ex) {
//				System.out.println("Normal click failed. Using JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}

//			System.out.println("Clicked successfully: " + carsButtonLocator.toString());

		} catch (TimeoutException e) {
			((JavascriptExecutor) driver).executeScript("alert('Error: Element not clickable or blocked on page!')");
			throw new RuntimeException("Click Failed for: " + carsButtonLocator + " -> " + e.getMessage());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void clickCarsItem() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		By carsButtonLocator = By.xpath("(//span[text()='Cars'])[2]");

		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(carsButtonLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

			try {
				element.click(); // Try normal click first
			} catch (ElementClickInterceptedException ex) {
//				System.out.println("Normal click failed. Using JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}
//			System.out.println("Clicked successfully: " + carsButtonLocator.toString());

		} catch (TimeoutException e) {
			((JavascriptExecutor) driver).executeScript("alert('Error: Element not clickable or blocked on page!')");
			throw new RuntimeException("Click Failed for: " + carsButtonLocator + " -> " + e.getMessage());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectMake(String make) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			WebElement makeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("make")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", makeSelect);

			String normalizedMake = make.trim().toLowerCase();

			List<WebElement> options = makeSelect.findElements(By.tagName("option"));

			WebElement bestMatch = null;

			for (WebElement option : options) {

				String optionText = option.getText().trim().toLowerCase();

				// ✅ 1️⃣ Exact match first
				if (optionText.equals(normalizedMake)) {
					bestMatch = option;
					break;
				}

				// ✅ 2️⃣ Partial match (Maruti → Maruti Suzuki)
				if (optionText.contains(normalizedMake)) {
					bestMatch = option;
				}
			}

			if (bestMatch == null) {
				throw new RuntimeException(make + " value not found in Make dropdown");
			}

			// 🔥 Set value via JS (Angular-safe)
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
					makeSelect, bestMatch.getAttribute("value"));

		} catch (Exception e) {
			throw new RuntimeException("Make selection failed: " + e.getMessage());
		}
	}

//	public void selectModel(String model) {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//		try {
//			WebElement modelSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("model")));
//
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", modelSelect);
//
//			// 🔥 CASE-INSENSITIVE lookup
//			WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
//					"//select[@id='model']/option[" + "translate(normalize-space(.)," + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
//							+ "'abcdefghijklmnopqrstuvwxyz') = '" + model.trim().toLowerCase() + "']")));
//
//			((JavascriptExecutor) driver).executeScript(
//					"arguments[0].value = arguments[1];"
//							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
//							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
//					modelSelect, option.getAttribute("value"));
//
////	        System.out.println("Model selected (case-insensitive): " + model);
//
//		} catch (TimeoutException e) {
//			throw new RuntimeException("Model field not found");
//		} catch (NoSuchElementException e) {
//			throw new RuntimeException(model + " value not found in Model dropdown");
//		}
//	}
	public void selectModel(String model) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement modelSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("model")));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", modelSelect);

		String sheetNormalized = normalizeVariant(model);

		List<WebElement> options = modelSelect.findElements(By.tagName("option"));

		WebElement bestMatch = null;
		int bestScore = -1;

		for (WebElement option : options) {

			String optionText = option.getText();
			String optionNormalized = normalizeVariant(optionText);

			// 🥇 RULE 1: Exact normalized match
			if (optionNormalized.equals(sheetNormalized)) {
				bestMatch = option;
				break;
			}

			// 🥈 RULE 2: Similarity score
			int score = calculateSimilarityScore(sheetNormalized, optionNormalized);

			// 🏁 RULE 3: Pick highest score
			if (score > bestScore) {
				bestScore = score;
				bestMatch = option;
			}
		}

		if (bestMatch == null || bestScore <= 0) {
			throw new RuntimeException("No close model match found for: " + model);
		}

		// 🔥 Select via JS (safe for Angular / React)
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].value = arguments[1];"
						+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
						+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
				modelSelect, bestMatch.getAttribute("value"));

		System.out.println("Model selected: " + bestMatch.getText());
	}

	public void variant() throws InterruptedException {

	    // Show alert to user
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("alert('Please select Variant Manually');");

	    WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofMinutes(5));
	    alertWait.until(ExpectedConditions.alertIsPresent());

	    try {
	    	alertWait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
	    }catch(TimeoutException e) {
	    	throw new RuntimeException("You did not accept the alert of Select Variant");
	    }
	    // 3. Now wait for Variant selection
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
	    System.out.println("Waiting for Variant selection");
	    try {
	        wait.until(driver -> {
	            WebElement parent = driver.findElement(
	                By.xpath("//label[text()='Variant *']/parent::div")
	            );
	            return parent.getAttribute("class").contains("rui-ZSwKI");
	        });
	    } catch (TimeoutException e) {
	        System.out.println("Please select the Variant again");
	        throw new RuntimeException("You didn't select the Variant.");
	    }
	    System.out.println("Variant has been selected.");
	}

	private String normalizeVariant(String text) {

		if (text == null)
			return "";

		return text.toLowerCase()
				// remove spaces & special characters
				.replaceAll("[^a-z0-9]", "").trim();
	}

	private int calculateSimilarityScore(String sheet, String option) {
		int score = 0;
		// reward common prefix / containment
		if (option.contains(sheet) || sheet.contains(option)) {
			score += 10;
		}
		// reward numeric match (engine size etc.)
		String sheetDigits = sheet.replaceAll("[^0-9]", "");
		String optionDigits = option.replaceAll("[^0-9]", "");

		if (!sheetDigits.isEmpty() && sheetDigits.equals(optionDigits)) {
			score += 5;
		}

		return score;
	}

	public void selectFuelType(String fuelType) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Convert fuel type name to matching element text
		String fuelXpath = "//button[@class='rui-pdy8W' and contains(text(),'" + fuelType + "')]";

		try {
			WebElement fuelBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fuelXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fuelBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", fuelBtn);
//			System.out.println("Fuel Type Selected: " + fuelType);

		} catch (Exception e) {
			System.out.println("Fuel type not found: " + fuelType);
			throw new RuntimeException("Please check the fuel type in excel!");
		}
	}

	public void selectTransmission(String transmission) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Builds locator based on visible button text
		String transmissionXpath = "//button[@class='rui-pdy8W' and contains(text(),'" + transmission + "')]";

		try {
			WebElement transmissionBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(transmissionXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", transmissionBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", transmissionBtn);
//			System.out.println("Transmission Selected: " + transmission);

		} catch (TimeoutException e) {
			System.out.println("Transmission not found: " + transmission);
			throw new RuntimeException("Please check the transmission value in Excel!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterMileage(int odometer) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By mileageLocator = By.id("mileage");

			WebElement mileageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(mileageLocator));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mileageInput);

			mileageInput.clear();

			// ✅ Convert double → String (no decimal part)
			mileageInput.sendKeys(String.valueOf(odometer));

			// System.out.println("Mileage entered: " + odometer);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter mileage: " + odometer);
			throw new RuntimeException("Mileage field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectNoOfowners(int ownerSerial) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Builds locator based on visible button text
		String noOfOwnersXpath = "//button[@class='rui-pdy8W' and contains(text(),'" + ownerSerial + "')]";

		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(noOfOwnersXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
//			System.out.println("Transmission Selected: " + no_of_owners);

		} catch (TimeoutException e) {
			System.out.println("No of owners not found: " + ownerSerial);
			throw new RuntimeException("Please check the no of owners value in Excel!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterAdTitle(String adTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By titleLocator = By.name("title");

			WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", titleInput);

			titleInput.clear();
			titleInput.sendKeys(adTitle);

//			System.out.println("Ad Title entered: " + adTitle);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter Ad Title: " + adTitle);
			throw new RuntimeException("Ad Title field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterDescription(String description) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By descLocator = By.id("description");

			WebElement descInput = wait.until(ExpectedConditions.visibilityOfElementLocated(descLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", descInput);

			descInput.clear();
			descInput.sendKeys(description);

//			System.out.println("Description entered: " + description);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter description: " + description);
			throw new RuntimeException("Description field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterPrice(int b2cPrice) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By priceLocator = By.name("price");

			WebElement priceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", priceInput);

			priceInput.clear();

			// ✅ Convert double → String
			priceInput.sendKeys(String.valueOf(b2cPrice));

			// System.out.println("Price entered: " + b2cPrice);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter price: " + b2cPrice);
			throw new RuntimeException("Price field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterYear(int year) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement yearInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("year")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", yearInput);

			yearInput.click();
			yearInput.clear();

			yearInput.sendKeys(String.valueOf(year));

			// 🔥 Trigger OLX validation
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));", yearInput);

//	        System.out.println("Year entered successfully: " + year);

		} catch (TimeoutException e) {
			throw new RuntimeException("Year field not found");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectState(String state) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			WebElement stateSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("State")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", stateSelect);

			// 🔥 CASE-INSENSITIVE lookup
			WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//select[@id='State']/option[" + "translate(normalize-space(.)," + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
							+ "'abcdefghijklmnopqrstuvwxyz') = '" + state.trim().toLowerCase() + "']")));

			// 🔥 OLX required events
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
					stateSelect, option.getAttribute("value"));

//	        System.out.println("State selected (case-insensitive): " + state);

		} catch (TimeoutException e) {
			throw new RuntimeException("State dropdown not found");
		} catch (NoSuchElementException e) {
			throw new RuntimeException(state + " value not found in State dropdown");
		}
	}

	public void selectCity(String city) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			WebElement citySelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("City")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", citySelect);

			// 🔥 CASE-INSENSITIVE option lookup
			WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
					"//select[@id='City']/option[" + "translate(normalize-space(.)," + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
							+ "'abcdefghijklmnopqrstuvwxyz') = '" + city.trim().toLowerCase() + "']")));

			// 🔥 OLX required events
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
					citySelect, option.getAttribute("value"));

//	        System.out.println("City selected (case-insensitive): " + city);

		} catch (TimeoutException e) {
			throw new RuntimeException("City dropdown not found");
		} catch (NoSuchElementException e) {
			throw new RuntimeException(city + " value not found in City dropdown");
		}
	}

	public void selectLocality(String locality) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

		try {
			WebElement localitySelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("Locality")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
					localitySelect);

			// 🔥 CASE-INSENSITIVE lookup
			WebElement option = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='Locality']/option["
							+ "translate(normalize-space(.)," + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
							+ "'abcdefghijklmnopqrstuvwxyz') = '" + locality.trim().toLowerCase() + "']")));

			// 🔥 OLX-required events
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].value = arguments[1];"
							+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
							+ "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
					localitySelect, option.getAttribute("value"));

//	        System.out.println("Locality selected (case-insensitive): " + locality);

		} catch (TimeoutException e) {
			throw new RuntimeException("Locality dropdown not found");
		} catch (NoSuchElementException e) {
			throw new RuntimeException(locality + " value not found in Locality dropdown");
		}
	}

	public void clickUploadBox() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement uploadBox = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'_2-TkM')]")));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", uploadBox);

		uploadBox.click(); // 🔥 THIS CREATES THE FILE INPUT
	}

	public void closeImagePopupFinal() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement imagePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("imageOnlyPopup")));
		} catch (TimeoutException e) {
			System.out.println("Not found");
		}
	}

	public WebElement getFileInput() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		return wait.until(driver -> {
			for (WebElement el : driver.findElements(By.xpath("//input[@type='file']"))) {
				if (el.isDisplayed() || el.getAttribute("style").contains("display")) {
					return el;
				}
			}
			return null;
		});
	}

	public void uploadImage(String filePath) {
		try {
//	        clickUploadBox();

			WebElement fileInput = getFileInput();
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].style.display='block'; arguments[0].style.opacity=1;", fileInput);

			fileInput.sendKeys(filePath);

//	        System.out.println("Image uploaded: " + filePath);

		} catch (Exception e) {
			// throw new RuntimeException("Image upload failed", e);
		}
	}

	public void uploadImageAtIndex(int index, String imagePath) throws AWTException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// 1️⃣ Get all Add Photo buttons
		List<WebElement> addPhotoButtons = wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-aut-id='imagesPreview']//button")));

		// Safety check
		if (index >= addPhotoButtons.size()) {
			throw new RuntimeException("No image slot available at index: " + index);
		}

		// 2️⃣ Click specific Add Photo button
		WebElement button = addPhotoButtons.get(index);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

		// 3️⃣ Wait for file input
		WebElement fileInput = wait.until(driver -> {
			for (WebElement el : driver.findElements(By.xpath("//input[@type='file']"))) {
				if (el.isDisplayed() || el.getAttribute("style") != null) {
					return el;
				}
			}
			return null;
		});
		try {
			fileInput.sendKeys(imagePath);
//			System.out.println("Uploaded image at index " + index + " → " + imagePath);

		} catch (Exception e) {
			Robot robot = new Robot();
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			System.out.println(imagePath + " Image Not found in Download Folder.");
		}
	}

	public void clickBackButton() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement element = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rui-CXaZ2.rui-XRg9H")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void details() {
		clickLoginButton();
		loginWithMobile();
	}
}
