package ABCPackage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login_Page {
	
    WebDriver driver;
    // we create the constructor of this class and pass the driver reference 
    public Login_Page(WebDriver driver)
    {
    	this.driver = driver;
    	PageFactory.initElements(driver, this);
    }    
 
    public void username(String username)
    {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	try {
	    	WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname=\"UserName\"]")));
	    	usernameField.sendKeys(username);
    	}catch(org.openqa.selenium.ElementClickInterceptedException e) {
    		WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname=\"UserName\"]")));
	    	usernameField.sendKeys(username);
    	}
    }
    public void password(String password)
    {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	try {
	    	WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname=\"Pwd\"]")));
	    	passwordField.sendKeys(password);
    	}catch(org.openqa.selenium.ElementClickInterceptedException e) {
    		WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@formcontrolname=\"Pwd\"]")));
	    	passwordField.sendKeys(password);
    	}
    }
    public void passwordChange() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    	try {
	    	WebElement pwdAlert = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),\"Your password has been changed after\")]")));
	    	String text =pwdAlert.getText();
    		ExtentReportListener.log(text, "Warning");
    	}catch(TimeoutException e) {
    	}
    }
    public void loginButton()
    {
    	try {
	    	WebElement loginBtn = driver.findElement(By.xpath("//button[@class=\"btn btn-block btn-primary\"]"));
	    	loginBtn.click();
    	}catch(org.openqa.selenium.ElementClickInterceptedException e) {
    		WebElement loginBtn = driver.findElement(By.xpath("//button[@class=\"btn btn-block btn-primary\"]"));
	    	loginBtn.click();
    	}
    }
    public void usernamePasswordAlert() throws InterruptedException {
    	Thread.sleep(1000);
    	try {
	    	try {
				driver.findElement(By.xpath("//p[normalize-space(text())=\"Please enter user name\"]"));
				throw new RuntimeException("Please enter username.");
	    	}catch(NoSuchElementException e) {
			}
			try {
	    		driver.findElement(By.xpath("//p[normalize-space(text())=\"Please enter password\"]"));
				throw new RuntimeException("Please enter Password.");
		    }catch(NoSuchElementException e) {
			}
			try {
				driver.findElement(By.xpath("//p[normalize-space(text())=\"Please check username and password\"]"));
				throw new RuntimeException("Please check username and Password.");
			}catch(NoSuchElementException e) {
			}
    	}catch(RuntimeException e) {
    		throw e;
    	}
    }
    public void login(String username,String password) throws InterruptedException
    {
    	username(username);
    	password(password);
    	loginButton();
    	//passwordChange();
    	usernamePasswordAlert();
    }
}
