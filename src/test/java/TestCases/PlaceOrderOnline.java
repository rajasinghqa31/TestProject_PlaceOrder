package TestCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlaceOrderOnline{

	WebDriver driver;
	String url ="https://stg.app2food.com/oloweb301/11002/#/";
	
	// Login Page Elements
	By threeLines = By.xpath("//i[@class='fa fa-navicon']");
	By signInLink = By.xpath("//a[text()='Sign In']");
	By emailField = By.xpath("//input[@id='loginMail']");
	By passwordField = By.xpath("//input[@id='loginPwd']");
	By loginButton = By.xpath("//button[text()='Login' and @class = 'btn btn-lg btn_primary signin']");
	By mainCategoryHeading = By.xpath("//div[text()='Main Category']");
	By avtarName = By.xpath("//span[@class='avatar_name']");	
	
	// Home Page Elements
	By firstFoodCategory = By.xpath("(//span[@class='overlay'])[1]");
	By firstFoodItem = By.xpath("(//button[@class = 'btn btn-sm quick_order' and text()='Add'])[1]");
	By pickupRadioButton = By.xpath("//input[@type='radio' and @id = 'pickup_option']");
	By saveAndProceedButton = By.xpath("//button[@id='saveOrderAddrBtn']");
	By mtestnRadioButton = By.xpath("//input[@data-mname='mtestn']");
	By AddItmeButton = By.xpath("//button[@id='addItemBtn']");
	By checkoutButton = By.xpath("//a[text()='Checkout']");
	By continueButton = By.xpath("//button[@type='button' and text()='Continue']");
	By codRadioButton = By.xpath("//label[contains(text(),'PAY CASH ON DELIVERY')]");
	By payButton = By.xpath("//button[contains(text(),'Pay $ ')]");
	By orderSuccessMessage = By.xpath("//h2[text()='Order Successfully placed']");
	
	public void waitForEleMentVisible(By name) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(name));
	}
	
	public void clickOnMenuButton() throws InterruptedException {
		waitForEleMentVisible(mainCategoryHeading);

		driver.findElement(threeLines).click();
	}
	
	public void login() throws IOException {
		driver.findElement(signInLink).click();
		driver.findElement(emailField).click();
		driver.findElement(emailField).clear();
		driver.findElement(emailField).sendKeys("rajasingh51289@gmail.com");
		driver.findElement(passwordField).click();
		driver.findElement(passwordField).clear();
		driver.findElement(passwordField).sendKeys("rajaraja");
		driver.findElement(loginButton).click();
		
		waitForEleMentVisible(avtarName);
	}
	
	public void selectOneCategory() {
		driver.findElement(firstFoodCategory).click();
		waitForEleMentVisible(firstFoodItem);
	}
	
	public void selectOneItem() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(firstFoodItem).click();
		waitForEleMentVisible(saveAndProceedButton);
	}
	
	public void saveAndProceed() throws InterruptedException {
		driver.findElement(pickupRadioButton).click();
		Thread.sleep(5000);
		driver.findElement(saveAndProceedButton).click();
		waitForEleMentVisible(AddItmeButton);
	}
	
	public void totalAddItem() {
		driver.findElement(mtestnRadioButton).click();
		driver.findElement(AddItmeButton).click();
		waitForEleMentVisible(checkoutButton);
	}
	
	public void checkout() {
		driver.findElement(checkoutButton).click();
		waitForEleMentVisible(continueButton);
		driver.findElement(continueButton).click();
		waitForEleMentVisible(payButton);
	}
	
	public void chosePaymentMethod() {
		driver.findElement(codRadioButton).click();
		driver.findElement(payButton).click();
		waitForEleMentVisible(orderSuccessMessage);
	}
	
	public void verifyOrderPlacedSuccessfully() {
		String actualMsg = driver.findElement(orderSuccessMessage).getText().toLowerCase();
		String expectedMsg = "Order Successfully placed";
		Assert.assertEquals(actualMsg, expectedMsg.toLowerCase());
	}
	
	@BeforeMethod
	public void initilizeTest() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void placeOrder() throws InterruptedException, IOException {
		
		Thread.sleep(10000);
		clickOnMenuButton();
		login();
		selectOneCategory();
		selectOneItem();
		saveAndProceed();
		totalAddItem();
		checkout();
		chosePaymentMethod();
		verifyOrderPlacedSuccessfully();
		
		Thread.sleep(10000);
	}
	
	@AfterMethod
	public void close() {
		driver.close();
	}

}
