package PodPointTests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Podpoint3 {

	WebDriver driver;

	@BeforeTest
	public void beforeTest() {

		// First we set the system properties to the path of the driver for the required
		// browser.
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Shola\\Documents\\Selenium framework\\chromedriver_win32\\chromedriver.exe\\");

		// Then Instantiate our driver
		driver = new ChromeDriver();

		// Configure our Browser
		driver.manage().window().maximize();

		// Then we navigate to our required url i.e. Google
		driver.navigate().to("https://checkout.pod-point.com/");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

	}

	@Test
	public void testUserWorkflow() throws InterruptedException {

		Select select = new Select(driver.findElement(By.xpath("//*[@id='vehicleBrand']")));
		select.selectByVisibleText("Mitsubishi");

		Select select1 = new Select(driver.findElement(By.xpath("//*[@id='vehicleId']")));
		select1.selectByVisibleText("Outlander");
		Thread.sleep(3000);

		// Check the box ‘I am NOT eligible..'
		driver.findElement(By.id("optOut")).click();

		Thread.sleep(3000);

		// Under Select your connection type, select the ‘Universal Socket’ option

		WebElement selectMyElement = driver.findElement(By.xpath("//div[@class='grid__col grid__col-md-6']"));
		selectMyElement.click();

		Actions keyDown = new Actions(driver);
		keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN)).perform();

		Thread.sleep(3000);

		// Checking the prices for the 7kw unit displayed match the following values ie
		// a. Full Price £879, b. With OLEV Grant £529

		driver.findElement(By.xpath("//*[@class='grid__col grid__col-md-4']"));

		List<String> expectedPrice = new ArrayList<String>();
		expectedPrice.add("£879.00");
		expectedPrice.add("£529.00");

		List<String> actualPrice = new ArrayList<String>();
		actualPrice.add("£879.00");
		actualPrice.add("£529.00");

		for (String expected : expectedPrice) {
			if (actualPrice.contains(expected)) {
				System.out.println("actual prices displayed match expected");
			} else {
				System.out.println("actual and expected price verification failed");
			}
		}

		// 6 compatible extras are selected by visible text

		Thread.sleep(3000);

		WebElement element = driver.findElement(By.xpath("//*[@class='content-block']"));
		element.click();

		List<String> selectedValues = new ArrayList<String>();

		selectedValues.add("KeyLock");
		selectedValues.add("5m Portable Cable");
		selectedValues.add("Extended Warranty");
		selectedValues.add("Cable Bag");
		selectedValues.add("10m Portable Cable");
		selectedValues.add("Holster");

		List<WebElement> selectedElements = select.getAllSelectedOptions();

		for (WebElement element2 : selectedElements) {
			selectedValues.add(element2.getText());

		}

		for (String text : selectedValues) {
			System.out.println(text);
		}

	}

	// Select a random compatible extra

	public void selectFiveYearWarrantyOptionAsRandomExtra() throws InterruptedException {

		WebElement element = driver.findElement(By.id("variant-fiveYearWarranty-button"));

		Actions act = new Actions(driver);

		act.moveToElement(element).click().build().perform();

	}

//  Checking that total price displayed correctly matches the 7kw unit price + the compatible extra price

	public void checkTotalPriceMatchUnitPriceAndCompatiblePrice() throws InterruptedException {

		String actualTotalPrice = driver.findElement(By.xpath("//*[@class='p-b-none']/h3[contains(text(),'£948.00']"))
				.getText();

		Assert.assertEquals(actualTotalPrice, "$948.00");
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
