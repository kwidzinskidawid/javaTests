package com.example.seleniumdemo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class SomeSiteTest {

	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void driverSetup() {
		// ChromeDrirver, FireforxDriver, ...
		System.setProperty("webdriver.chrome.driver", "/home/PJWSTK/s11995/Pobrane/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void homePage(){
		driver.get("http://www.benchmark.pl/");
		
		element = driver.findElement(By.linkText("Dziś premiera gry Dark Souls 3"));
		assertNotNull(element);
	}
	
	@Test
	public void polsatPage(){
		driver.get("http://www.benchmark.pl/");
		driver.findElement(By.linkText("Dziś premiera gry Dark Souls 3")).click();
		element = driver.findElement(By.linkText("Dziś premiera gry Dark Souls 3"));
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);

		try {
			FileUtils.copyFile(screenshot, new File("/home/PJWSTK/s11995/Pobrane/polsat.png"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

	@AfterClass
	public static void cleanp() {
		driver.quit();
	}
}
