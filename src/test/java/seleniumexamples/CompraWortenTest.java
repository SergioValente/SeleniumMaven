package seleniumexamples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;

@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompraWortenTest {

	static WebDriver driver;
	JavascriptExecutor jse;

	static String productDescription;
	static String productPriceCurrent;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\sergio.valente\\eclipse-workspace\\SeleniumExample\\drivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("test-type");
		options.addArguments("incognito");
		options.addArguments("enable-strict-powerful-feature-restrictions");
		options.addArguments("disable-geolocation");
		//options.addArguments("disable-notifications");
		
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("All tests Runned.");
		driver.quit();
	}

	@Test
	public void stage01_testLogin() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		driver.get("http://www.worten.pt");
		System.out.println("Test1");
		assertEquals("Worten Online | Tudo o que precisa em Worten.pt - 24H", driver.getTitle());
 		
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("INICIAR SESSÃO"))).click();
		
		//driver.findElement(By.cssSelector("input#email")).sendKeys("sergio134@sapo.pt");
		//driver.findElement(By.cssSelector("input#pass")).sendKeys("wortenNotFree");
		driver.findElement(By.xpath("//*[@id=\"accountLogin\"]/div/div/div/div[1]/form/div[2]/div/button")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.dropbtn_login_menu")));
		driver.navigate().back();
		Thread.sleep(1000);

		assertEquals("https://www.worten.pt/cliente/conta#/myDashboard", driver.getCurrentUrl());
		assertEquals("Área de Cliente | Início de Sessão como Cliente | Worten.pt", driver.getTitle());
	}

	@Test
	public void stage02_testChoosePromotionLink() {
		System.out.println("Test2");
		By locator = By.xpath("//*[@id=\"main-menu\"]/div[1]/ul/li[2]/a");

		driver.findElement(locator).click();

		assertEquals("https://www.worten.pt/promocoes", driver.getCurrentUrl());
		assertEquals("Promoções | Worten.pt", driver.getTitle());
	}

	@Test
	public void stage03_testChoosePromotionToBuy() throws Exception {
		System.out.println("Test3");
		scrollDown();

		String xpath = "//*[@id=\"products-list-block\"]/div[8]/div/a/div/div[1]/div[1]/h3";
		driver.findElement(By.xpath(xpath)).click();

		productDescription = driver
				.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/section/div/section[2]/div/div/div[1]/span"))
				.getText();
		productPriceCurrent = driver.findElement(By.cssSelector("span.w-product__price__current"))
				.getAttribute("content").replaceAll(",", ".");

		// Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("button.w-button-primary.qa-product-options__add-cart-linkto.w-checkout-button")));

		assertEquals("ADICIONAR AO CARRINHO", element.getText());
	}

	@Test
	public void stage04_testBuyPromotion() throws Exception {
		System.out.println("Test4");

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Click on "ADICIONAR AO CARRINHO"
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("button.w-button-primary.qa-product-options__add-cart-linkto.w-checkout-button")))
				.click();

		// Click on shopping cart symbol
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.w-shop-cart-icon-mobile.qa-header__shop-cart.cart-has-products"))).click();

		// Wait for description of product in the shopping cart to be available
		WebElement productDescriptionElementShoppingCart = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector(".checkout-product__title-name > a:nth-child(1)")));

		String productDescriptionShoppingCart = productDescriptionElementShoppingCart.getText();

		WebElement productPriceElementShoppingCart = driver
				.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/div/div[2]/div[5]/span[2]/input"));

		String productPriceShoppingCart = productPriceElementShoppingCart.getAttribute("value");

		assertEquals("Carrinho de Compras | Worten.pt", driver.getTitle());
		assertEquals(productDescription, productDescriptionShoppingCart);
		assertEquals(productPriceCurrent, productPriceShoppingCart);
	}

	@Test
	public void stage05_testPlusAndMinusButtonsShoppingCart() throws Exception {
		System.out.println("Test5");

		WebElement quantityShoppingCart = driver
				.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/div/div[2]/div[4]/span[2]/span[2]"));
		Thread.sleep(5000);
		int quantityBeforeClick = Integer.parseInt(quantityShoppingCart.getText());

		// Testing plus button
		WebElement plusButtonShoppingCart = driver
				.findElement(By.cssSelector("span.checkout-qtd-btn.w-qtd-cell__button.w-qtd-cell__more-button"));
		plusButtonShoppingCart.click();
		Thread.sleep(6000);
		quantityShoppingCart = driver
				.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/div/div[2]/div[4]/span[2]/span[2]"));
		int quantityAfterClick = Integer.parseInt(quantityShoppingCart.getText());

		assertEquals((quantityBeforeClick + 1), quantityAfterClick);

		// Testing minus button
		WebElement minusButtonShoppingCart = driver
				.findElement(By.cssSelector("span.checkout-qtd-btn.w-qtd-cell__button.w-qtd-cell__button__less"));
		minusButtonShoppingCart.click();
		Thread.sleep(6000);
		quantityShoppingCart = driver
				.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/div/div[2]/div[4]/span[2]/span[2]"));
		quantityAfterClick = Integer.parseInt(quantityShoppingCart.getText());

		assertEquals(quantityBeforeClick, quantityAfterClick);
	}

	@Test
	public void stage06_testDeleteProductFromShoppingCart() throws Exception {

		driver.findElement(
				By.cssSelector("div.checkout-product__delete-product:nth-child(6) > a:nth-child(1) > i:nth-child(1)"))
				.click();
		Thread.sleep(3000);
		String expectedCartMessage = "O seu carrinho está vazio de momento.";
		String emptyCartMessage = driver.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/div/p")).getText();

		assertEquals(expectedCartMessage, emptyCartMessage);
	}

	private void scrollDown() {
		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 1000)");
	}
}
