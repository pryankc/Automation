package WebAutomation;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HiraliGoogleSearchAutomation {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.gecko.driver", "C:\\softwares\\selenium\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		// launch the chrome browser  
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS); 
		//Waiting for page to load after clicking on an element
		driver.get("https://www.google.co.in");
		//open the application url
		driver.findElement(By.id("lst-ib")).sendKeys("yahoo");
		// enter yahoo in the search textbox
		driver.findElement(By.name("btnG")).click();
		// click on the Search button
		Thread.sleep(3000);
		driver.findElement(By.xpath("//h3/a")).click();
		// click on the first link of search result
		driver.navigate().back();
		//go back to previous page
		Thread.sleep(3000);
		driver.findElement(By.id("lst-ib")).clear();
		//clear the search box
		driver.findElement(By.id("lst-ib")).sendKeys("selenium");
		//enter selenium in the search textbox
		driver.findElement(By.name("btnG")).click();
		// click on the Search button
	}

}
