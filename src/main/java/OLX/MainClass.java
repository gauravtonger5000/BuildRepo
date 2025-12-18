package OLX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

public class MainClass {
	public MainClass(WebDriver driver) {
		this.driver = driver;
	}

	WebDriver driver;
	private String excelFilePath;
	private String extentReportPath; // Path to save Extent Reports
	private ExtentReportListener reportListener;

	@BeforeClass
	public void setUp() throws IOException, EncryptedDocumentException, InterruptedException {
		driverClass dc = new driverClass(driver);
		driver = dc.browserSel();

//		excelFilePath = System.getProperty("excelFilePath", "src/main/resources/OlxData.xlsx");
		excelFilePath = System.getProperty("excelFilePath", "C:\\Users\\ACS-90\\Downloads\\OlxData.xlsx");

		extentReportPath = System.getProperty("extentReportPath",
				"C:\\Users\\ACS-90\\Downloads\\OLX_Report.html");

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
	public void test() throws InterruptedException, FileNotFoundException, IOException {
		ExtentTest test = reportListener.startTest("OLX Report");
		TestClass tc = new TestClass(driver);
		File excelFile = new File(excelFilePath);
		FileInputStream fis = new FileInputStream(excelFile);
		Workbook workbook = new XSSFWorkbook(fis);
		DataFormatter formatter = new DataFormatter();
		Sheet sheet1 = workbook.getSheet("Credentials");
		
		Sheet sheet2 = workbook.getSheet("Data");
		Row row1 = sheet1.getRow(1);
		String url = formatter.formatCellValue(row1.getCell(0));
		driver.get(url);
		for (int i = 1; i <= 1; i++) {
			Row row = sheet1.getRow(i);
			Row row2 = sheet2.getRow(i);

			String username = formatter.formatCellValue(row.getCell(1));
			String password = formatter.formatCellValue(row.getCell(2));
			
			String make = formatter.formatCellValue(row2.getCell(0));
			String model = formatter.formatCellValue(row2.getCell(1));
			String variant = formatter.formatCellValue(row2.getCell(2));
			String year = formatter.formatCellValue(row2.getCell(3));
			String fuelType = formatter.formatCellValue(row2.getCell(4));
			String transmission = formatter.formatCellValue(row2.getCell(5));
			String mileage = formatter.formatCellValue(row2.getCell(6));
			String owners = formatter.formatCellValue(row2.getCell(7));
			String title = formatter.formatCellValue(row2.getCell(8));
			String description = formatter.formatCellValue(row2.getCell(9));
			String price = formatter.formatCellValue(row2.getCell(10));
			String state = formatter.formatCellValue(row2.getCell(11));
			String city = formatter.formatCellValue(row2.getCell(12));
			String locality = formatter.formatCellValue(row2.getCell(13));
			String file_path = formatter.formatCellValue(row2.getCell(14));

			System.out.println("Make: " + make);
			System.out.println("Model: " + model);
			System.out.println("Variant: " + variant);
			System.out.println("Year: " + year);
			System.out.println("Fuel Type: " + fuelType);
			System.out.println("Transmission: " + transmission);
			System.out.println("Mileage: " + mileage);
			System.out.println("Owners: " + owners);
			System.out.println("Title: " + title);
			System.out.println("Description: " + description);
			System.out.println("Price: " + price);
			
			try {
				tc.details();
				tc.enterMobileNo(username);
				tc.nextBtn();
				tc.password(password);
				tc.loginBtn();
				tc.clickSellButton();
				tc.clickCarsButton();
				tc.clickCarsItem();
				Thread.sleep(4000);
				tc.selectMakeHuman(make);
				Thread.sleep(500);
				tc.selectModelHuman(model);
				tc.selectVariant1(variant);
				tc.fillCarDetails(make, model, variant, year, fuelType, transmission, mileage, owners, title, description, price);
				tc.uploadImage(file_path);

				tc.selectStateHuman(state);
				tc.selectCityHuman(city);
				tc.selectLocalityHuman(locality);
			} catch (Exception e) {
				System.out.println(e.getMessage() + " in Row No. " + i);
				reportListener.log(e.getMessage() + " in Row No. " + i, "FAIL");
				// ne.signout();
//				driver.get(url);
			}
			reportListener.flushReport();
		}
		System.out.println("Completed");
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

	public static void main(String[] args) throws InterruptedException, IOException {
		MainClass mainTest = new MainClass(null);
		try {
			mainTest.setUp(); // Browser launches here only ONCE
			mainTest.test();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
