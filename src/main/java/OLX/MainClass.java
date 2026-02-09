package OLX;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import API.OlxAPIService;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainClass {

	// Comment It to make it testNG
	public MainClass(WebDriver driver) {
		this.driver = driver;
	}

	WebDriver driver;
	private String excelFilePath;
	private String extentReportPath; // Path to save Extent Reports
	private ExtentReportListener reportListener;
	public static String make;
	public static String model;
	public static String variant;
	public static int registrationYear;
	public static String fuelType;
	public static String transmission;
	public static int b2CPrice;
	public static int ownerSerial;
	public static int odometer;
	public static int totalImages;
	public static JSONArray imageArray;
	public static String url;
	public static List<String> reg;
	public static String userHome = System.getProperty("user.home");

	@BeforeClass
	public void setUp() throws IOException, EncryptedDocumentException, InterruptedException, URISyntaxException {

		driverClass dc = new driverClass(driver);
		driver = dc.browserSel();
        String userHome = System.getProperty("user.home");

		//excelFilePath = System.getProperty("excelFilePath", "src/main/resources/OlxData.xlsx");
		excelFilePath = System.getProperty("excelFilePath", userHome+"\\Downloads\\OlxData.xlsx");

		extentReportPath = System.getProperty("extentReportPath", userHome+"\\Downloads\\OLX_Report.html");

		if (excelFilePath == null || excelFilePath.isEmpty()) {
			throw new RuntimeException("Excel file path not provided!");
		}
		if (extentReportPath == null || extentReportPath.isEmpty()) {
			throw new RuntimeException("Extent Report path not provided!");
		}

		reportListener = new ExtentReportListener();
		reportListener.setupReport(extentReportPath);
	}

	@Test
	public void test() throws InterruptedException, FileNotFoundException, IOException, URISyntaxException {
		ExtentTest test = reportListener.startTest("OLX Report");
		TestClass tc = new TestClass(driver);
		File excelFile = new File(excelFilePath);
		FileInputStream fis = new FileInputStream(excelFile);
		Workbook workbook = new XSSFWorkbook(fis);
		DataFormatter formatter = new DataFormatter();
		Sheet sheet1 = workbook.getSheet("Credentials");

		Sheet sheet2 = workbook.getSheet("Data");
		Row row1 = sheet1.getRow(1);

		String urlWeb = formatter.formatCellValue(row1.getCell(0));

		Row row = sheet1.getRow(1);
		Row row2 = sheet2.getRow(1);

		String username = formatter.formatCellValue(row.getCell(1));
		String password = formatter.formatCellValue(row.getCell(2));
//		String title = formatter.formatCellValue(row2.getCell(8));
		String description = formatter.formatCellValue(row2.getCell(0));
		String state = formatter.formatCellValue(row2.getCell(1));
		String city = formatter.formatCellValue(row2.getCell(2));
		String locality = formatter.formatCellValue(row2.getCell(3));
		driver.get(urlWeb);

		reg = OlxAPIService.getAllRegistrationNo();
		int totalRegNo = reg.size();
		System.out.println("Total Registration Numbers: " + totalRegNo);

		tc.details();
		tc.enterMobileNo(username);
		tc.nextBtn();
		tc.password(password);
		tc.loginBtn();
		Thread.sleep(1000);
//		tc.clickSellButton();
//		tc.clickCarsButton();
//		tc.clickCarsItem();
		for (int i = 1; i <= 1; i++) {
			String allResponse = OlxAPIService.getAllDataAsString();
//			System.out.println(allResponse);
			reg = OlxAPIService.getAllRegistrationNo();
			int n = 1;
			for (String reg_no : reg) {
				System.out.println(reg_no);
//				System.out.println(n);
			n++;
			String apiData = OlxAPIService.getVehicleDetailsByRegNo(reg_no);
//			System.out.println(apiData);

			JSONObject json = new JSONObject(apiData);
			try {
				make = json.getString("make").trim();
				model = json.getString("model");
				registrationYear = json.getInt("registrationYear");
				fuelType = json.getString("fuelType");
				transmission = json.getString("transmission");
				odometer = json.getInt("odometer");
				ownerSerial = json.getInt("ownerSerial");
				b2CPrice = json.getInt("b2CPrice");
				variant = json.getString("variant");
//				System.out.println("Model: "+model);
//				System.out.println("No of Onwers: "+ownerSerial);
//				System.out.println(fuelType);
//				System.out.println(transmission);
			} catch (Exception e) {

			}
			String imageUrl = json.getString("imageUrl");
			imageArray = new JSONArray(imageUrl);

			totalImages = imageArray.length();

			for (int k = 0; k < totalImages; k++) {
				JSONObject imageObj = imageArray.getJSONObject(k);
				// It download in Headless Mode
				for (String key : imageObj.keySet()) {

					url = imageObj.getString(key);

//					if (url.startsWith("http") && (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg"))) {
					if (url.contains(".")) {

//						System.out.println("Downloading image: " + url);
						String fileName = url.substring(url.lastIndexOf("/") + 1);
						String savePath = userHome+"\\Downloads\\" + fileName;

						try (InputStream in = new URL(url).openStream()) {
							Files.copy(in, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);
						}
					}
				}
			}
			for (int m = 0; m < imageArray.length(); m++) {
				JSONObject imageObj = imageArray.getJSONObject(m);

				for (String key : imageObj.keySet()) {
					url = imageObj.getString(key);
//					String fileName = url.substring(url.lastIndexOf("/") + 1);
//					System.out.println("File Name: " + fileName);
				}
			}
			try {
				tc.clickSellButton();
				tc.clickCarsButton();
				Thread.sleep(1000);
				tc.clickCarsItem();
				tc.selectMake(make);
				Thread.sleep(500);
				tc.selectModel(model);
				tc.variant();

				tc.enterYear(registrationYear);
				tc.selectFuelType(fuelType);
				tc.selectTransmission(transmission);
				tc.enterMileage(odometer);
				tc.selectNoOfowners(ownerSerial);
				tc.enterPrice(b2CPrice);

				tc.enterAdTitle(make+" "+model+" ("+registrationYear+")");
				tc.enterDescription(description);
				for (int j = 0; j < totalImages; j++) {
					JSONObject imageObj = imageArray.getJSONObject(j);

					for (String key : imageObj.keySet()) {
						url = imageObj.getString(key);
						String fileName = url.substring(url.lastIndexOf("/") + 1);
//						System.out.println("File Name: " + fileName);

//				        String imagePath = "C:\\Users\\ACS-90\\Downloads\\" + fileName;
						String userHome = System.getProperty("user.home");
						String imagePath = userHome + "\\Downloads\\" + fileName;

						tc.uploadImageAtIndex(j, imagePath);
//				        tc.uploadImage(imagePath);
						try {
						    Files.deleteIfExists(Paths.get(imagePath));
//						    System.out.println("Deleted image: " + imagePath);
						} catch (IOException e) {
						    System.out.println("Failed to delete image: " + imagePath);
						}
					}
				}
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				tc.selectState(state);
				tc.selectCity(city);
				tc.selectLocality(locality);
				Thread.sleep(30000);
				tc.clickBackButton();
				Alert alert = driver.switchTo().alert();   // switch to alert
				String alertText = alert.getText();        // store alert text
				System.out.println("Alert Message: " + alertText);
				alert.accept();                            // click OK
				Thread.sleep(500);
//   			test.pass("Everything is working fine...");
				tc.clickBackButton();

			} catch (Exception e) {
				System.out.println(e.getMessage() + " in Row No. " + i);
				reportListener.log(e.getMessage() + " in Row No. " + i, "FAIL");
				// ne.signout();
//				driver.get(url);
			}
			reportListener.flushReport();
		}
		}
		test.pass("Everything is working as expected.");

		System.out.println("Completed");
		reportListener.flushReport();
		workbook.close();
		// driver.quit();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		reportListener.flushReport();
	}

	// Comment this method to make it testNG
	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriver driver = null;

		MainClass mainTest = new MainClass(driver);
		try {
			mainTest.setUp(); // Browser launches here only ONCE
			mainTest.test();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
