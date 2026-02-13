package OLX;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
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
import java.nio.file.Files;
import java.nio.file.Paths;

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
	public static String registrationNo;

	public static int totalImages;
	public static List<String> chassisId;
	public static JSONArray imageArray;
	public static String url;
	public static List<String> reg;

	public static String userHome = System.getProperty("user.home");

	@BeforeClass
	public void setUp() throws IOException, EncryptedDocumentException, InterruptedException, URISyntaxException {

//		driverClass dc = new driverClass(driver);
		driver = driverClass.browserSel();
		String userHome = System.getProperty("user.home");

		excelFilePath = System.getProperty("excelFilePath", userHome + "\\Downloads\\OlxData.xlsx");

		extentReportPath = System.getProperty("extentReportPath", userHome + "\\Downloads\\OLX_Report.html");

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
		ExtentTest test = ExtentReportListener.startTest("OLX Report");
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
//		String description = formatter.formatCellValue(row2.getCell(0));
		String state = formatter.formatCellValue(row2.getCell(1));
		String city = formatter.formatCellValue(row2.getCell(2));
		String locality = formatter.formatCellValue(row2.getCell(3));
		driver.get(urlWeb);
		String allResponse = OlxAPIService.getAllDataAsString();
//		System.out.println("All Response: " + allResponse);
		reg = OlxAPIService.getAllRegistrationNo();
		int totalRegNo = reg.size();
//		System.out.println("Total Registration Numbers: " + totalRegNo);

		tc.details();
		tc.enterMobileNo(username);
		tc.nextBtn();
		tc.password(password);
		tc.loginBtn();
		Thread.sleep(1000);
		for (int i = 1; i <= 1; i++) {
			chassisId = OlxAPIService.getAllChassisId();
			for (String chassis : chassisId) {
				System.out.println("Chassis Id: " + chassis);

				String apiDataImage = OlxAPIService.getImage(chassis);
//				System.out.println(apiDataImage);

				// Call the Image API Method
				JSONArray imageArray = new JSONArray(apiDataImage);
				TestClass.downloadImages(imageArray);

				// Get All the Data Using Chassis no
				String apiData = OlxAPIService.getVehicleDetailsByChassisId(chassis);
//				System.out.println("API Data: " + apiData);

				// Get the USP data using the Chassis No
				String allResponseUSP = OlxAPIService.getAllDataForChassisIdUSP(chassis);
//				System.out.println("USP Data: " + allResponseUSP);

				JSONArray jsonArray = new JSONArray(allResponseUSP);

				StringBuilder description = new StringBuilder();
				for (int m = 0; m < jsonArray.length(); m++) {

					JSONObject obj = jsonArray.getJSONObject(m); // ✅ use m

					String label = obj.optString("label").trim();
					String answer = obj.optString("answer").trim();
//					System.out.println("Label : " + label);
//					System.out.println("Answer: " + answer);
					description.append(label).append(" : ").append(answer).append("\n");
				}
				System.out.println(description);
				// Here we are calling the data that have need
				JSONObject json = new JSONObject(apiData);
				try {
					registrationNo = json.getString("registrationNo");
					make = json.getString("makeDesc").trim();
					model = json.getString("modalName");
					registrationYear = json.getInt("mfryear");
					fuelType = json.getString("fuelType");
					transmission = json.getString("transmissionDesc");
					odometer = json.getInt("odometer");
					ownerSerial = json.getInt("ownerSerial");
					b2CPrice = json.getInt("expectedSellingPrice");
					variant = String.valueOf(json.get("transmission"));

//					System.out.println("Registration No.: " + registrationNo);
//					System.out.println("Make: " + make);
//					System.out.println("Model: " + model);
//					System.out.println("Registration Year: " + registrationYear);
//					System.out.println("Fuel Type: " + fuelType);
//					System.out.println("Transmission: " + transmission);
//					System.out.println("Odometer: " + odometer);
//					System.out.println("Owner Serial: " + ownerSerial);
//					System.out.println("Expected Selling Price: " + b2CPrice);
//					System.out.println("Variant: " + variant);
//  				System.out.println("=============================================================");

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
					tc.selectTransmission(transmission, registrationNo);
					tc.enterMileage(odometer);
					tc.selectNoOfowners(ownerSerial);
					tc.enterPrice(b2CPrice);
					tc.enterAdTitle(make + " " + model + " (" + registrationYear + ")");
					
					
					// If the USP have no data or empty then use this to enter description
					if (!description.isEmpty()) {
						tc.enterDescription(description.toString());
					} else {
						System.out.println("No Data found in USP.");
						tc.enterDescription("Description has been filled.");
					}
//

					String baseUrl = "https://bttacsstorage.blob.core.windows.net/btt/";

					String saveDir = System.getProperty("user.home") + File.separator + "Downloads";
					File folder = new File(saveDir);
					if (!folder.exists())
						folder.mkdirs();

					for (int x = 0; x < imageArray.length(); x++) {

						JSONObject obj = imageArray.getJSONObject(x); // FIXED (was i)

						if (!obj.has("answer") || obj.isNull("answer"))
							continue;

						String imageName = obj.getString("answer").trim();
						if (imageName.isEmpty())
							continue;

						totalImages++;

						String folderType = obj.getString("folderType");

						// Clean filename
						imageName = imageName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

						String imageUrl = baseUrl + folderType + "/" + imageName;

						// Save into Downloads folder
						String userHome = System.getProperty("user.home");
						String imagePath = userHome + "\\Downloads\\" + imageName;

						// Download image
						TestClass.downloadImage(imageUrl, imagePath);

						// Upload using local file path
						tc.uploadImageAtIndex(x, imagePath);

						// Delete file after upload
						try {
							Files.deleteIfExists(Paths.get(imagePath));
						} catch (IOException e) {
							System.out.println("Failed to delete image: " + imagePath);
						}
					}
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
					tc.selectState(state);
					tc.selectCity(city);
					tc.selectLocality(locality);
					Thread.sleep(100000);
					tc.clickBackButton();
					Alert alert = driver.switchTo().alert(); // switch to alert
					String alertText = alert.getText(); // store alert text
					System.out.println("Alert Message: " + alertText);
					alert.accept(); // click OK
					Thread.sleep(500);
					test.pass("Everything is working fine...");
					tc.clickBackButton();
				} catch (Exception e) {
					System.out.println("Issue in retrieving data from the APIs: " + e.getMessage());
				}
			}
		}
		System.out.println("Completed");
		reportListener.flushReport();
		test.pass("Everything is working as expected.");
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
