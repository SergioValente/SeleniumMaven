package seleniumexamples;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompraWorten {

	WebDriver driver;
	JavascriptExecutor jse;
	
	public static void main(String[] args) throws InterruptedException {
		
		String xpath = "//*[@id=\"products-list-block\"]/div[8]/div/a/div/div[1]/div[1]/h3";
		
		CompraWorten teste = new CompraWorten();
		teste.invokeBrowser();
		teste.loginWorten();
		//Thread.sleep(6000);
		teste.choosePromotionLink();
		teste.scrollDown();
		teste.choosePromotionToBuy(xpath);
		teste.buyPromotion();
		Thread.sleep(2000);
		teste.removeProductFromCart();
		Thread.sleep(2000);
		teste.scrollUp();
		xpath = "//*[@id=\"products-list-block\"]/div[2]/div/a/div/div[1]/div[1]/h3";
		teste.choosePromotionToBuy(xpath);
		teste.buyPromotion();
		Thread.sleep(2000);
		teste.removeProductFromCart();
		teste.logout();
	}
	
	public void invokeBrowser() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sergio.valente\\eclipse-workspace\\SeleniumExample\\drivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("test-type");
		options.addArguments("incognito");
		options.addArguments("enable-strict-powerful-feature-restrictions");
		options.addArguments("disable-geolocation");
		options.addArguments("disable-notifications");
		
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}
	
	public void loginWorten() {
		driver.get("http://www.worten.pt");
		driver.findElement(By.linkText("INICIAR SESSÃO")).click();
		
		driver.findElement(By.cssSelector("input#email")).sendKeys("sergio134@sapo.pt");
		driver.findElement(By.cssSelector("input#pass")).sendKeys("wortenNotFree");
		driver.findElement(By.xpath("//*[@id=\"accountLogin\"]/div/div/div/div[1]/form/div[2]/div/button")).click();
	}
	
	public void choosePromotionLink() {
		By locator = By.xpath("//*[@id=\"main-menu\"]/div[1]/ul/li[2]/a");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		WebElement linkToPromotions = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		System.out.println(linkToPromotions.getAttribute("class"));
		
		driver.findElement(locator).click();
	}
	
	public void choosePromotionToBuy(String xpath) {	
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public void buyPromotion() throws InterruptedException {	
		driver.findElement(By.xpath("//*[@id=\"undefined\"]/div/div/section/div/section[2]/div/div/div[2]/div[1]/div[2]/button")).click();
		Thread.sleep(4000);
	}
	
	public void removeProductFromCart() {
		driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[1]/div[2]/div[6]/a[1]/i[1]")).click();
		driver.navigate().back();
		driver.navigate().back();
	}
	
	public void logout() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"main-menu\"]/div[2]/ul/div/button")).click();
		driver.findElement(By.xpath("//*[@id=\"main-menu\"]/div[2]/ul/div/ul/li[3]/a")).click();
		Thread.sleep(2000);
		driver.close();
	}
	
	private void scrollDown() {
		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 1000)");
	}
	
	private void scrollUp() {
		jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(1000, 0)");
	}
}
