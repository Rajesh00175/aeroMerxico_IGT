package com.world.aeromexico;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class Flight {
	WebDriver Driver;
	String testURL = "https://world.aeromexico.com/en/uk";
	
	@BeforeTest(alwaysRun=true)		
	public void BrowserSetup(){
		
		String aeroDriverPathFirefox = "D:\\Testing\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", aeroDriverPathFirefox);
		Driver = new FirefoxDriver();
		Driver.get(testURL);
		Driver.manage().window().maximize();			
		
	}
	
  @Test(enabled = false)
  public void searchFlight() throws ParseException, IOException, InterruptedException {
	  	Driver.get(testURL);
	  	FileInputStream FIS = null;
		File scr = new File(System.getProperty("user.dir") + "\\TestData", "Flight_Booking.xlsx");
		FIS = new FileInputStream(scr);
		XSSFWorkbook aeroMexicoWB = new XSSFWorkbook(FIS);
		System.out.println("Workbook = " + aeroMexicoWB);
		Sheet Sheet1 = aeroMexicoWB.getSheet("Sheet1");
		/// TO READ ALL DATA FROM SHEET
		int totalRows = Sheet1.getLastRowNum() - Sheet1.getFirstRowNum();
		Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		for (int row = 1; row <= totalRows; row++) {
			String To = Sheet1.getRow(row).getCell(1).getStringCellValue();
			System.out.println("Data From Excell Sheet - To Location " + To);			
			String From = Sheet1.getRow(row).getCell(2).getStringCellValue();
			System.out.println("Data From Excell Sheet - From Location " + From);
			String testDepartureDate = Sheet1.getRow(row).getCell(3).getStringCellValue();
			System.out.println("Data From Excell Sheet - Departure Date :" + testDepartureDate);
			String testReturnDate = Sheet1.getRow(row).getCell(4).getStringCellValue();
			System.out.println("Data From Excell Sheet - Return Date :" + testReturnDate);
			String testAdult = Sheet1.getRow(row).getCell(5).getStringCellValue();
			System.out.println("Data From Excell Sheet - Adults :" + testAdult);
			String testChild = Sheet1.getRow(row).getCell(6).getStringCellValue();
			System.out.println("Data From Excell Sheet - Child :" + testChild);
			Thread.sleep(1000);
			/*String ch = "Flights";
			List<WebElement> menu = Driver.findElements(By.xpath("//ul[@class='menu']/li/a/span"));
			for(WebElement mn: menu) {
				String choices = mn.getText();
				if(choices.equalsIgnoreCase(ch)) {
					mn.click();
					break;
				}
			}*/

			WebElement currencyChange = Driver.findElement(By.xpath("//*[@id='flight']/fieldset/div[2]/div"));

			/// xpath("//div[@class=\"select\"]"));
			currencyChange.click();
			List<WebElement> pickCurrency = Driver.findElements(By.xpath("//ul[@class='countries show']/li"));
			for (WebElement curreny : pickCurrency) {
				String currencyName = curreny.getText();
				if (currencyName.equalsIgnoreCase("United States (Eng) USD")) {
					curreny.click();
					break;
				}
			}

			// tripType = Driver.findElement(By.id("one"));
			// tripType.click();
			Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement travelingTo = Driver.findElement(By.id("from"));
			travelingTo.clear();
			travelingTo.sendKeys("N");
			Driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<WebElement> travelingTOO = Driver.findElements(By.xpath("//ul[@id='ui-id-3']/li"));
			for (WebElement travlTo : travelingTOO) {
				String toDestination = travlTo.getText();
				if (toDestination.equalsIgnoreCase(To)) {
					travlTo.click();
					break;
				}

			}
			Driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement travelingFrom = Driver.findElement(By.id("to"));
			travelingFrom.sendKeys("L");
			List<WebElement> travelingFROM = Driver.findElements(By.xpath("//ul[@id='ui-id-4']/li"));
			for (WebElement travlFrom : travelingFROM) {
				String fromDestination = travlFrom.getText();
				if (fromDestination.equalsIgnoreCase(From)) {
					travlFrom.click();
					break;
				}
			}

			Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// String testDepartureDate = "30/01/2018";
			SimpleDateFormat source = new SimpleDateFormat("dd/MM/yyyy");
			Date parseDateDeparture = source.parse(testDepartureDate);
			SimpleDateFormat sdfDepartureDate = new SimpleDateFormat("EEE MMMM dd yyyy");
			String departureDate = sdfDepartureDate.format(parseDateDeparture);
			System.out.println("Departure Date :" + departureDate);
			String[] splitDepartureDate = departureDate.split(" ");
			System.out.println(splitDepartureDate[0] + "----" + splitDepartureDate[1] + "---" + splitDepartureDate[2]
					+ "---" + splitDepartureDate[3]);
			String testDepartureDay = splitDepartureDate[2];
			String testDepartureMonth = splitDepartureDate[1];
			System.out.println("Test Departure Day----" + testDepartureDay);
			System.out.println("Test Departure Month ----" + testDepartureMonth);

			// String testReturnDate = "31/01/2018";
			SimpleDateFormat returnSource = new SimpleDateFormat("dd/MM/yyyy");
			Date parseReturnDate = returnSource.parse(testReturnDate);
			SimpleDateFormat sdfReturnDate = new SimpleDateFormat("EEE MMMM dd yyyy");
			String returnDate = sdfReturnDate.format(parseReturnDate);
			System.out.println("Return Date :" + returnDate);
			String[] splitReturnDate = returnDate.split(" ");
			System.out.println(splitReturnDate[0] + "----" + splitReturnDate[1] + "---" + splitReturnDate[2] + "---"
					+ splitReturnDate[3]);
			String testReturnDay = splitReturnDate[2];
			String testReturnMonth = splitReturnDate[1];
			System.out.println("Test Return Day ----" + testReturnDay);
			System.out.println("Test Return Month ----" + testReturnMonth);

			// pick month name for departure date Element
			WebElement departureDateELE;
			departureDateELE = Driver.findElement(By.id("date-init"));
			departureDateELE.click();

			while (!Driver.findElement(By.className("ui-datepicker-title")).getText().contains(testDepartureMonth)) // Handle
																													// the
																													// month
			{
				Driver.findElement(By.linkText("Next")).click();
			}
			int depDateCount = Driver.findElements(By.className("ui-state-default")).size();

			for (int i = 0; i < depDateCount; i++)

			{

				String depDateValue = Driver.findElements(By.className("ui-state-default")).get(i).getText();
				if (depDateValue.equalsIgnoreCase(testDepartureDay)) {
					Driver.findElements(By.className("ui-state-default")).get(i).click();
					break;
				}

			}

			// pick date for Return date Element

			WebElement returnDateELE;
			returnDateELE = Driver.findElement(By.id("date-end"));
			returnDateELE.click();
			while (!Driver.findElement(By.className("ui-datepicker-title")).getText().contains(testReturnMonth)) // Handle
																													// the
																													// month
			{
				Driver.findElement(By.linkText("Next")).click();
			}

			List<WebElement> return_m = Driver.findElements(By.className("ui-state-default"));

			for (WebElement return_dayPick : return_m) {
				String returnDay = return_dayPick.getText();
				if (returnDay.equalsIgnoreCase(testReturnDay)) {
					return_dayPick.click();
					break;
				}

			}

			// Select Adult
			WebElement adult = Driver.findElement(By.xpath("//div[@id='adultsF']"));
			adult.click();
			List<WebElement> adults = Driver.findElements(By.xpath("//ul[@class='selector-class show']/li"));
			for (WebElement adlt : adults) {
				String adultStr = adlt.getText();
				if (adultStr.equalsIgnoreCase(testAdult)) {
					adlt.click();
					break;
				}
			}

			// Select Children

			WebElement child = Driver.findElement(By.xpath("//div[@id='childrenF']"));
			child.click();
			List<WebElement> children = Driver.findElements(By.xpath("//ul[@class='selector-class show']/li"));
			for (WebElement chld : children) {
				String childStr = chld.getText();
				if (childStr.equalsIgnoreCase(testChild)) {
					chld.click();
					break;
				}
			}

			Driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			WebElement searchButton = Driver
					.findElement(By.xpath("//div[@class='input-row two buttons']/input[@class='btn b-orange']"));
			searchButton.click();
			Driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);

			try {
				String expected_Page_Title = "Flights Page Aeroméxico booking";
				//for Firefox only
				WebDriverWait wait = new WebDriverWait(Driver,20);
				wait.until(ExpectedConditions.titleIs(expected_Page_Title));
				String actual_Page_Title = Driver.getTitle();
				System.out.println(actual_Page_Title);
				
				Assert.assertEquals(actual_Page_Title, expected_Page_Title);
				System.out.println("Test Pass...");
				Sheet1.getRow(row).createCell(7).setCellValue("Pass");
				FileOutputStream fout = new FileOutputStream(
						new File(System.getProperty("user.dir") + "\\TestData", "Flight_Booking.xlsx"));
				aeroMexicoWB.write(fout);
				File src = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
				try {
					// now copy the screenshot to desired location using copyFile method
					FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\ScreenShot\\Test_Pass_"
							+ System.currentTimeMillis() + ".png"));
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
			} catch (AssertionError e) {
				// Take screenshot and store as a file format
				File src = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
				try {
					// now copy the screenshot to desired location using copyFile method
					FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\ScreenShot\\Error_"
							+ System.currentTimeMillis() + ".png"));
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				System.out.println(e.getMessage());
			}
		}
		aeroMexicoWB.close();
		Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  @Test(enabled=false)
  public void vacations() throws ParseException, InterruptedException {
	  Driver.get(testURL);
	// Select value from menu
		String ch = "Vacations";
		List<WebElement> menu = Driver.findElements(By.xpath("//ul[@class='menu']/li/a/span"));
		for (WebElement mn : menu) {
			String choices = mn.getText();
			if (choices.equalsIgnoreCase(ch)) {
				mn.click();
				break;
			}
		}
		
		//Select FROM and TO destinations element
		WebElement htlTravelingFrom = Driver.findElement(By.id("origin-hotel"));
		htlTravelingFrom.click();
		htlTravelingFrom.sendKeys("Ma");
		Thread.sleep(6000);

		
		
		
		/*WebDriverWait wait1 = new WebDriverWait(Driver,20);
		wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id='ui-id-5']/li")));*/
		Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		List<WebElement> htltravelingFROM = Driver.findElements(By.id("//*[@id='ui-id-20']"));
		for (WebElement htltravlFrom : htltravelingFROM) {
			String htlFromDestination = htltravlFrom.getText();
			if (htlFromDestination.equalsIgnoreCase("Madrid (MAD) Aeropuerto de Barajas, España")) {
				htltravlFrom.click();
				break;
			}
		}
		
		WebElement htlTravelingTo = Driver.findElement(By.id("dest-hotel"));
		htlTravelingTo.click();
		Thread.sleep(6000);
		
		//Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//htlTravelingTo.sendKeys("Ci");
		
		List<WebElement> htltravelingTO = Driver.findElements(By.xpath("//ul[@id='ui-id-6']/li"));
		for (WebElement htltravlTo : htltravelingTO) {
			String htlToDestination = htltravlTo.getText();
			if (htlToDestination.equalsIgnoreCase("Ciudad de México (MEX)")) {
				htltravlTo.click();
				break;
			}
		}
		//Select Dates
		String testDepartureDate = "30/03/2018";
		SimpleDateFormat source = new SimpleDateFormat("dd/MM/yyyy");
		Date parseDateDeparture = source.parse(testDepartureDate);
		SimpleDateFormat sdfDepartureDate = new SimpleDateFormat("EEE MMMM dd yyyy");
		String departureDate = sdfDepartureDate.format(parseDateDeparture);
		System.out.println("Departure Date :" + departureDate);
		String[] splitDepartureDate = departureDate.split(" ");
		System.out.println(splitDepartureDate[0] + "----" + splitDepartureDate[1] + "---" + splitDepartureDate[2]
				+ "---" + splitDepartureDate[3]);
		String testDepartureDay = splitDepartureDate[2];
		String testDepartureMonth = splitDepartureDate[1];
		System.out.println("Test Departure Day----" + testDepartureDay);
		System.out.println("Test Departure Month ----" + testDepartureMonth);

		String testReturnDate = "31/03/2018";
		SimpleDateFormat returnSource = new SimpleDateFormat("dd/MM/yyyy");
		Date parseReturnDate = returnSource.parse(testReturnDate);
		SimpleDateFormat sdfReturnDate = new SimpleDateFormat("EEE MMMM dd yyyy");
		String returnDate = sdfReturnDate.format(parseReturnDate);
		System.out.println("Return Date :" + returnDate);
		String[] splitReturnDate = returnDate.split(" ");
		System.out.println(splitReturnDate[0] + "----" + splitReturnDate[1] + "---" + splitReturnDate[2] + "---"
				+ splitReturnDate[3]);
		String testReturnDay = splitReturnDate[2];
		String testReturnMonth = splitReturnDate[1];
		System.out.println("Test Return Day ----" + testReturnDay);
		System.out.println("Test Return Month ----" + testReturnMonth);

		// pick month name for departure date Element
		WebElement htlDepartureDateELE;
		htlDepartureDateELE = Driver.findElement(By.id("date-hotel-init"));
		htlDepartureDateELE.click();

		while (!Driver.findElement(By.className("ui-datepicker-title")).getText().contains(testDepartureMonth)) // Handle
																												// the
																												// month
		{
			Driver.findElement(By.linkText("Next")).click();
		}
		int depDateCount = Driver.findElements(By.className("ui-state-default")).size();

		for (int i = 0; i < depDateCount; i++)

		{

			String depDateValue = Driver.findElements(By.className("ui-state-default")).get(i).getText();
			if (depDateValue.equalsIgnoreCase(testDepartureDay)) {
				Driver.findElements(By.className("ui-state-default")).get(i).click();
				break;
			}

		}

		// pick date for Return date Element

		WebElement htlReturnDateELE;
		htlReturnDateELE = Driver.findElement(By.id("date-hotel-end"));
		htlReturnDateELE.click();
		while (!Driver.findElement(By.className("ui-datepicker-title")).getText().contains(testReturnMonth)) // Handle
																												// the
																												// month
		{
			Driver.findElement(By.linkText("Next")).click();
		}

		List<WebElement> return_m = Driver.findElements(By.className("ui-state-default"));

		for (WebElement return_dayPick : return_m) {
			String returnDay = return_dayPick.getText();
			if (returnDay.equalsIgnoreCase(testReturnDay)) {
				return_dayPick.click();
				break;
			}

		}
		
	  
  }
  @Test
  public void Hotels() throws ParseException {
	  //Select Date
	  
		String testDepartureDate = "30/03/2018";
		SimpleDateFormat source = new SimpleDateFormat("dd/MM/yyyy");
		Date parseDateDeparture = source.parse(testDepartureDate);
		SimpleDateFormat sdfDepartureDate = new SimpleDateFormat("EEE MMMM dd yyyy");
		String departureDate = sdfDepartureDate.format(parseDateDeparture);
		System.out.println("Departure Date :" + departureDate);
		String[] splitDepartureDate = departureDate.split(" ");
		System.out.println(splitDepartureDate[0] + "----" + splitDepartureDate[1] + "---" + splitDepartureDate[2]
				+ "---" + splitDepartureDate[3]);
		String testDepartureDay = splitDepartureDate[2];
		String testDepartureMonth = splitDepartureDate[1];
		System.out.println("Test Departure Day----" + testDepartureDay);
		System.out.println("Test Departure Month ----" + testDepartureMonth);

		String testReturnDate = "31/03/2018";
		SimpleDateFormat returnSource = new SimpleDateFormat("dd/MM/yyyy");
		Date parseReturnDate = returnSource.parse(testReturnDate);
		SimpleDateFormat sdfReturnDate = new SimpleDateFormat("EEE MMMM dd yyyy");
		String returnDate = sdfReturnDate.format(parseReturnDate);
		System.out.println("Return Date :" + returnDate);
		String[] splitReturnDate = returnDate.split(" ");
		System.out.println(splitReturnDate[0] + "----" + splitReturnDate[1] + "---" + splitReturnDate[2] + "---"
				+ splitReturnDate[3]);
		String testReturnDay = splitReturnDate[2];
		String testReturnMonth = splitReturnDate[1];
		System.out.println("Test Return Day ----" + testReturnDay);
		System.out.println("Test Return Month ----" + testReturnMonth);

		//Driver.get(testURL);
		// Select value from menu
		String ch = "Hotels";
		List<WebElement> menu = Driver.findElements(By.xpath("//ul[@class='menu']/li/a/span"));
		for (WebElement mn : menu) {
			String choices = mn.getText();
			if (choices.equalsIgnoreCase(ch)) {
				mn.click();
				break;
			}
		}

		WebElement bed = Driver.findElement(By.id("origin-bed"));
		bed.click();
		bed.sendKeys("London");
		Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Select Dates

		// pick month name for departure date Element
		WebElement htlDepartureDateELE;
		htlDepartureDateELE = Driver.findElement(By.id("date-bed-init"));
		htlDepartureDateELE.click();
		System.out.println(htlDepartureDateELE.getText());
		WebElement asdf= Driver.findElement(By.className("ui-datepicker-title"));
		String XX = asdf.getText();
		System.out.println(XX);
		while (!Driver.findElement(By.className("ui-datepicker-title")).getText().contains(testDepartureMonth)) // Handle
																												// the
																												// month
		{
			Driver.findElement(By.linkText("Next")).click();
		}
		int depDateCount = Driver.findElements(By.className("ui-state-default")).size();
		System.out.println(depDateCount);
		/*for (int i = 0; i < depDateCount; i++)

		{

			String depDateValue = Driver.findElements(By.className("ui-state-default")).get(i).getText();
			if (depDateValue.equalsIgnoreCase(testDepartureDay)) {
				Driver.findElements(By.className("ui-state-default")).get(i).click();
				break;
			}

		}*/

		// pick date for Return date Element

		//WebElement htlReturnDateELE;
		//htlReturnDateELE = Driver.findElement(By.id("date-bed-end"));
		//htlReturnDateELE.click();
		/*while (!Driver.findElement(By.className("ui-datepicker-month")).getText().contains(testReturnMonth)) // Handle
																												// the
																												// month
		{
			Driver.findElement(By.linkText("Next")).click();
		}

		List<WebElement> return_m = Driver.findElements(By.className("ui-state-default"));

		for (WebElement return_dayPick : return_m) {
			String returnDay = return_dayPick.getText();
			if (returnDay.equalsIgnoreCase(testReturnDay)) {
				return_dayPick.click();
				break;
			}

		}
*/
		/*WebElement SearchHotel = Driver.findElement(By.className("btn b-orange"));
		SearchHotel.click();
		Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  */
  }

  @AfterTest
  public void afterTest() {	
	  Driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  Driver.quit();
  }

}
