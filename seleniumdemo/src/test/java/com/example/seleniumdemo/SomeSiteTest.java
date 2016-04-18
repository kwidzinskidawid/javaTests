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
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dave\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void homePage(){
		driver.get("localhost:3000");
		
		element = driver.findElement(By.cssSelector("#topPlaces"));
		assertNotNull(element);
	}
	
	@Test
	public void loginSuccessPage(){
		driver.get("localhost:3000");
		driver.findElement(By.id("signIn")).click();
		WebElement login = driver.findElement(By.id("login"));
		login.sendKeys("test");
		WebElement password = driver.findElement(By.id("passwordL"));
		password.sendKeys("test");
		driver.findElement(By.id("signInButton")).click();
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);
	    
		try {
			FileUtils.copyFile(screenshot, new File("C:\\Users\\Dave\\Pobrane\\polsat.png"));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	@Test
	public void loginFailPage(){
		driver.get("localhost:3000");
		driver.findElement(By.id("signIn")).click();
		WebElement login = driver.findElement(By.id("login"));
		login.sendKeys("testWrong");
		WebElement password = driver.findElement(By.id("passwordL"));
		password.sendKeys("testWrong");
		driver.findElement(By.id("signInButton")).click();
		
	    WebElement error = driver.findElement(By.cssSelector(".logIn > p:nth-child(5)"));
	    assertTrue(error.isDisplayed());
	
	}

	
	@AfterClass
	public static void cleanp() {
		driver.quit();
	}
}
