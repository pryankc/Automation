package WebAutomation;

import static com.jayway.restassured.RestAssured.given;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jayway.restassured.path.json.JsonPath;

public class UniqueUrlAutomation {
	
	public static void main(String args[]){
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//BrowserDrivers//geckodriver.exe");
		  WebDriver driver = new FirefoxDriver();
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  driver.get("http://services.ce3c.be/ciprg/");
		  List<WebElement> all_links_webpage = driver.findElements(By.tagName("a")); 
		  System.out.println("Total no of links Available: " + all_links_webpage.size());
		  int k = all_links_webpage.size();
		  System.out.println("List of links Available: ");
		  for(int i=0;i<k;i++)
		  {
		  String link = all_links_webpage.get(i).getAttribute("href");
		  //System.out.println(link);
		  if(link.equalsIgnoreCase("http://software77.net/cgi-bin/ip-country/geo-ip.pl") || link.equalsIgnoreCase("http://www.ce3c.be") || link.equalsIgnoreCase("?countrys=INDIA") || link.equalsIgnoreCase("?countrys="))
		  {
			  //return;
		  }else
		  {
			  System.out.println(all_links_webpage.get(i).getText());
			  driver.findElement(By.linkText(all_links_webpage.get(i).getText())).click();;
		  }
		  
		  }		  
		  
		  
		  /*WebDriverWait wait = new WebDriverWait(driver, 25);
		  
		  driver.get("https://www.ftcash.com/app/pay/store/nomisma");
		  driver.findElement(By.id("name")).sendKeys("Priyank");
		  driver.findElement(By.id("email")).sendKeys("pryankc@gmail.com");
		  driver.findElement(By.id("mobile")).sendKeys("9769283101");
		  driver.findElement(By.id("amount")).sendKeys("1");
		  driver.findElement(By.id("description")).sendKeys("testing");
		  driver.findElement(By.xpath("//*[@id='step1']/div/div/button")).click();
		  driver.findElement(By.id("code")).sendKeys("ftcash");
		  wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='promo-form']/div[4]/p"), "Please enter otp to validate coupon!"));
		  String res = given().when().get("https://uat-ftcash.com/interface/exequery.php?sql=select vMobile ,vOtp as otp FROM unique_link_otp where  vMobile = '9769283101' AND date BETWEEN timestamp(DATE_SUB(NOW(), INTERVAL 30 MINUTE))  AND timestamp(NOW()) AND eUsed = 'Active'&command=read").then().assertThat().statusCode(200).contentType("application/json").extract().asString();
		  JsonPath jsonpath = new JsonPath(res);
		  System.out.println(jsonpath.getString("data.otp"));
		  driver.findElement(By.xpath("//*[@id='otp']")).sendKeys(jsonpath.getString("data.otp"));
		  //Object result = js.executeScript("return window.isApplicationLoaded;");
		  //System.out.println(result);
		  //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  //WebDriverWait wait = new WebDriverWait(driver, 15);
		  //wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("html/body/div[1]/div[5]/div/div/div/form/div[5]/p[1]"), "Your coupon code applied successfully"));
		  wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='promo-form']/div[5]/p[1]"), "Your coupon code applied successfully"));
		  boolean fname = driver.findElement(By.xpath("//*[@id='offer-to-payment']")).isEnabled();
		  System.out.print(fname);
		  //driver.findElement(By.id("offer-to-payment")).click();
		  driver.findElement(By.xpath("//*[@id='offer-to-payment']")).click();
		  
		  driver.findElement(By.xpath("//*[@id='step_nav_3']/a")).click();
		  wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='card-charge-alert']/div[2]/h6/label"), "Only Indian Cards are acceptable"));
		  driver.findElement(By.xpath("//*[@id='cc_name']")).sendKeys("Priyank");
		  driver.findElement(By.xpath("//*[@id='checkout_card_number']")).sendKeys("4585460004508520");
		  Select listbox = new Select(driver.findElement(By.xpath("//*[@id='card-form']/div[4]/div[1]/div/div[1]/select")));
		  listbox.selectByValue("Aug");
		  listbox = new Select(driver.findElement(By.xpath("//*[@id='years_pop']")));
		  listbox.selectByValue("2021");
		  driver.findElement(By.xpath("//*[@id='cvv']")).sendKeys("111");
		  driver.findElement(By.xpath("//*[@id='pay_by_cc']")).click();
		  wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("html/body/form/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[1]/td[2]"), "FT CASH"));
		  List<WebElement> txtfields = driver.findElements(By.xpath("//input[@type='text' or @type='password']"));
		  for(int a=0; a<txtfields.size();a++){   
			   txtfields.get(a).sendKeys("Text"+(a+1));  
			  }
		  System.out.println(txtfields.size());
		  txtfields.get(0).sendKeys("blahblah");
		  driver.findElement(By.xpath("html/body/form/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[8]/td[2]/table/tbody/tr/td[1]/input")).click();
		  */
	}

}
