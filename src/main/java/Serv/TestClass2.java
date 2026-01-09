package Serv;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestClass2 {
	WebDriver driver;

	public TestClass2(WebDriver driver) {
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
					System.out.println("Click successful");
					ExtentReportListener.log("Login button clicked successfully", "PASS");
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
			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
			System.out.println("Login With Mobile No. button clicked successfully");
			ExtentReportListener.log("Login With Mobile No. button clicked successfully", "PASS");

		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Login With Mobile element is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void username(String username) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By enterMob = By.xpath("//input[@id=\"user_name\"]");
		try {
//			WebElement mobNofield = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder=\"Phone Number\"]")));
			WebElement mobNofield = wait.until(ExpectedConditions.elementToBeClickable(enterMob));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mobNofield);
			Thread.sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", mobNofield);
			mobNofield.sendKeys(username);
			System.out.println("Mobile No. enetered successfully");
			ExtentReportListener.log("Mobile No. enetered successfully", "PASS");

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
			System.out.println("Password entered successfully");
			ExtentReportListener.log("Password entered successfully", "PASS");

		} catch (TimeoutException e) {
			throw new RuntimeException("Waited For 30 Seconds but Password Field is not clickable");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// button[@data-aut-id="submitBtn"]
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
					System.out.println("Next Button clicked successfully");
					ExtentReportListener.log("Next Button clicked successfully", "PASS");
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
		By loginBtn = By.xpath("//input[@value=\"Login\"]");

		try {
			WebElement loginBtnElement = wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
			for (int i = 1; i <= 3; i++) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtnElement);
					Thread.sleep(500);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtnElement);
//					nextBtnBtn.click();
					System.out.println("Login Button clicked successfully");
					ExtentReportListener.log("Login Button clicked successfully", "PASS");
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
			System.out.println("Not get any Error.");
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

			sellBtn.click();
			System.out.println("Sell button clicked successfully.");
		} catch (TimeoutException e) {
			// Show error message using JavaScript Alert
			((JavascriptExecutor) driver).executeScript(
					"alert('Sell button not visible. Please check the element or wait for page load!');");

			System.err.println("Sell button not clickable within timeout.");
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
//	        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(carsButtonLocator));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

			try {
				element.click(); // Try normal click first
			} catch (ElementClickInterceptedException ex) {
				System.out.println("Normal click failed. Using JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}

			System.out.println("Clicked successfully: " + carsButtonLocator.toString());

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
				System.out.println("Normal click failed. Using JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}

			System.out.println("Clicked successfully: " + carsButtonLocator.toString());

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
			// Step 1: Click the visible make dropdown box
			WebElement makeDropdown = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-aut-id='ddmake']")));
			makeDropdown.click();

			Thread.sleep(1000); // small wait for dropdown to open

			// Step 2: Click required Make by visible text
			WebElement makeOption = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//li[@role='option']//span[contains(text(),'" + make + "')]")));
			makeOption.click();

			System.out.println("Make selected: " + make);

		} catch (Exception e) {
			System.out.println("Make dropdown not found: " + e.getMessage());
			throw new RuntimeException("Failed selecting Make");
		}
	}

	public void selectModel(String model) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id=\"model\"]")));
			Select s = new Select(element);
			try {
				s.selectByVisibleText(model);
			} catch (org.openqa.selenium.NoSuchElementException e) {
				throw new RuntimeException(model + " value not found in Model dropdown");
			}
		} catch (TimeoutException e) {
			throw new RuntimeException("Model field not found");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectVariant(String variant) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id=\"variant\"]")));
			Select s = new Select(element);
			try {
				s.selectByVisibleText(variant);
			} catch (org.openqa.selenium.NoSuchElementException e) {
				throw new RuntimeException(variant + " value not found in Variant dropdown");
			}
		} catch (TimeoutException e) {
			throw new RuntimeException("Variant field not found");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void year(String year) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id=\"make\"]")));
			element.sendKeys(year);
		} catch (TimeoutException e) {
			throw new RuntimeException("Year field not found");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectFuelType(String fuelType) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Convert fuel type name to matching element text
		String fuelXpath = "//button[@class='rui-pdy8W' and contains(text(),'" + fuelType + "')]";

		try {
			WebElement fuelBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fuelXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fuelBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", fuelBtn);
			System.out.println("Fuel Type Selected: " + fuelType);

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
			WebElement transmissionBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(transmissionXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", transmissionBtn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", transmissionBtn);
			System.out.println("Transmission Selected: " + transmission);

		} catch (TimeoutException e) {
			System.out.println("Transmission not found: " + transmission);
			throw new RuntimeException("Please check the transmission value in Excel!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterMileage(String mileage) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By mileageLocator = By.id("mileage");

			WebElement mileageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(mileageLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mileageInput);

			mileageInput.clear();
			mileageInput.sendKeys(mileage);

			System.out.println("Mileage entered: " + mileage);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter mileage: " + mileage);
			throw new RuntimeException("Mileage field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void selectNoOfowners(String no_of_owners) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Builds locator based on visible button text
		String noOfOwnersXpath = "//button[@class='rui-pdy8W' and contains(text(),'" + no_of_owners + "')]";

		try {
			WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(noOfOwnersXpath)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
			System.out.println("Transmission Selected: " + no_of_owners);

		} catch (TimeoutException e) {
			System.out.println("No of owners not found: " + no_of_owners);
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

			System.out.println("Ad Title entered: " + adTitle);

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

			System.out.println("Description entered: " + description);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter description: " + description);
			throw new RuntimeException("Description field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void enterPrice(String price) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			By priceLocator = By.name("price");

			WebElement priceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", priceInput);

			priceInput.clear();
			priceInput.sendKeys(price);

			System.out.println("Price entered: " + price);

		} catch (TimeoutException e) {
			System.out.println("Unable to enter price: " + price);
			throw new RuntimeException("Price field not found or not interactable!");
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void fillCarDetails(String make, String model, String variant, String year, String fuelType,
			String transmission, String mileage, String owners, String title, String description, String price)
			throws InterruptedException {
		selectMake(make);
		Thread.sleep(2000);
		selectModel(model);
		Thread.sleep(2000);
		selectVariant(variant);
		year(year);
		selectFuelType(fuelType);
		selectTransmission(transmission);
		enterMileage(mileage);
		selectNoOfowners(owners);
		enterAdTitle(title);
		enterDescription(description);
		enterPrice(price);
	}

	public void details() {
		clickLoginButton();
		loginWithMobile();
	}
}
