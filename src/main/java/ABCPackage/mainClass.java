package ABCPackage;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class mainClass {
	WebDriver driver =null;

	@BeforeClass
	public void setup() throws EncryptedDocumentException, InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-gpu", "--window-size=1920,1080", "--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://preowned.acsinfotech.com/login");
		String title = driver.getTitle();
		System.out.println(title);
	}
	@Test
	public void test() throws IOException, InterruptedException {
		Login_Page login = new Login_Page(driver);
		String excelFilePath = "./src/main/resources/ProcurementPagesTestUpdated.xlsx";
		File excelFile = new File(excelFilePath);
		FileInputStream fis = new FileInputStream(excelFile);
		Workbook workbook = new XSSFWorkbook(fis);
		DataFormatter formatter = new DataFormatter();
		Sheet sheet = workbook.getSheet("New Enquiry");
		for (int i = 1; i <= 1; i++) {
			Row row = sheet.getRow(i); 
			String username = formatter.formatCellValue(row.getCell(0));
			String password = formatter.formatCellValue(row.getCell(1));
			login.login(username, password);
			String title = driver.getTitle();
			String expectedTitle = "Banyan Tree - UAT -";
			assertEquals(title, expectedTitle);
			System.out.println("Equals");
		}
		workbook.close();
	}
	@AfterClass
	    public void tearDown() {
	        driver.quit();
	}
}
