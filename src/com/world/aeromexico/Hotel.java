package com.world.aeromexico;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Hotel {

	public static void main(String[] args) {
		WebDriver Driver;
		String testURL = "https://world.aeromexico.com/en/uk";
		String aeroDriverPathFirefox = "D:\\Testing\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver", aeroDriverPathFirefox);
		Driver = new FirefoxDriver();
		Driver.get(testURL);
		Driver.manage().window().maximize();

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
		WebElement htlDepartureDateELE;
		htlDepartureDateELE = Driver.findElement(By.id("date-bed-init"));
		htlDepartureDateELE.click();
		System.out.println(htlDepartureDateELE.getText());
		WebElement asdf= Driver.findElement(By.className("ui-datepicker-title"));
		String XX = asdf.getText();
		System.out.println(XX);
		while(!Driver.findElement(By.className("ui-datepicker-title")).getText().contains("July"))     // Handle the  month
		 {
			Driver.findElement(By.linkText("Next")).click();
		  }
	}

}
