package com.example.webguidemo.pages;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Home extends WebDriverPage {

	WebDriver driver;
	String screenshotPath;

	public Home(WebDriverProvider driverProvider) {
		super(driverProvider);
		driver = driverProvider.get();
		
		screenshotPath = System.getProperty("screenshotPath");
	}

	public void open() {
		get("http://www.seleniumframework.com/Practiceform/");
		manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	public void clickRadioButton(int n) {
		findElement(By.id("vfb-7-"+n)).click();
	}
	
	public void clickCheckBox(int n) {
		int option = n - 1; 
		findElement(By.id("vfb-6-"+option)).click();
	}
	
	public boolean isRadioButtonSelected(int n) {
		return findElement(By.id("vfb-7-"+n)).isSelected();
	}
	
	public boolean isCheckBoxSelected(int n) {
		int option = n - 1; 
		return findElement(By.id("vfb-6-"+option)).isSelected();
	}
	
	public void enterVerificationText(String number) {
		findElement(By.xpath("//*[@id='vfb-3']")).clear();
		findElement(By.xpath("//*[@id='vfb-3']")).sendKeys(number);
	}
	
	public boolean isErrorLabelVisible() {
		return findElement(By.xpath("//*[@id='item-vfb-2']/ul/li[1]/span/label[1]")).isDisplayed();
	}
	
	public String getVerificationErrorText() {
		return findElement(By.cssSelector("#item-vfb-2 > ul > li.vfb-item.vfb-item-secret > span > label.vfb-error")).getText();
	}
	
	public void clickAlert() {
		driver.navigate().refresh();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		WebElement el = findElement(By.xpath("//*[@id='alert']"));
		jse.executeScript("arguments[0].click();", el);
		
	}
	
	public boolean isAlertVisible() {
	    try { 
	        driver.switchTo().alert(); 
	        return true; 
	    }
	    catch (NoAlertPresentException Ex) { 
	        return false; 
	    } 
	}
	
	public boolean hasErrorClass() {
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    assertNotNull(screenshot);
		try {
			FileUtils.copyFile(screenshot, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		WebElement el = findElement(By.id("vfb-3"));

		return el.getAttribute("class").contains("vfb-error");
	}
}
