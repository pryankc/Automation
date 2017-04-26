package WebAutomation;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class AnkushGoogleSearchAutomation {
	public static WebDriver driver;
	public static void main(String[] args) throws Exception
	{
			//STEP 1
			// Setting up Webdriver properties 
		System.setProperty("webdriver.gecko.driver", "C:\\softwares\\selenium\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		try {
			//STEP 2
			driver.get("http://google.co.in");
			//Maximize browser window
			driver.manage().window().maximize();
			Thread.sleep(2000);
		
			//STEP 3
			String keyword = "ftcash";
			driver.findElement(By.className("gsfi")).sendKeys(keyword);
			Thread.sleep(2000);
		
			//To click on search Button
			String cssButton = "button[name ='btnG']";
			driver.findElement(By.cssSelector(cssButton)).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
			//STEP 4
			//To select first URL Link
			driver.findElement(By.cssSelector("h3.r > a")).click();
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			Thread.sleep(2000);
		
			//To print Url Link
			String url=driver.getCurrentUrl();
			System.out.println(url);
		
			//STEP 5 -  go back to the previous page
			//Navigate to previous page
			driver.navigate().back();
			Thread.sleep(2000);
		
			//STEP 6
			//Clear the search box and search for another word
			driver.findElement(By.className("gsfi")).clear();
			Thread.sleep(2000);
		
			//Search for next keyword
			String keyword1 = "paypal";
			driver.findElement(By.className("gsfi")).sendKeys(keyword1);
			Thread.sleep(2000);
			driver.findElement(By.cssSelector(cssButton)).click();
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		
			//To print Url Link
			String u=driver.getCurrentUrl();
			System.out.println(u);
		
			//To Exit Application
			Thread.sleep(6000);
			driver.close();  //
			driver.quit();
			} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	

}
