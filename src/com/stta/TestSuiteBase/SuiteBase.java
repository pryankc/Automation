//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.stta.TestSuiteBase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.proxy.jetty.http.HttpRequest;
import net.lightbody.bmp.proxy.jetty.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.stta.utility.Read_XLS;

public class SuiteBase {	
	public static Read_XLS TestSuiteListExcel=null;
	public static Read_XLS TestCaseListExcelOne=null;
	public static Read_XLS TestCaseListExcelTwo=null;
	public static Read_XLS TestCaseListExcelAPITest=null;
	public static Read_XLS TestCaseListExcelUITest=null;
	public static Logger Add_Log = null;
	public boolean BrowseralreadyLoaded=false;
	public static Properties Param = null;
	public static Properties Object = null;
	public static WebDriver driver=null;
	public static WebDriver ExistingchromeBrowser;
	public static WebDriver ExistingmozillaBrowser;
	public static WebDriver ExistingIEBrowser;
	public AndroidDriver androidDriver;
	public static TouchAction action;
	public static Dimension size;
	public static int startx,endx,starty,endy;
	public static int startX,endX,yAxis,moveToXDirectionAt;
	public static ArrayList al;
	public static Boolean iselementpresent;
	public static String sFileName = "E:/SeleniumEasy.har";
	public static BrowserMobProxy proxy;
	public static BrowserMobProxyServer server;


	//public static WebDriverWait wait = new WebDriverWait(androidDriver, 50);
	public void init() throws IOException{
		//To Initialize logger service.
		Add_Log = Logger.getLogger("rootLogger");				
		
		//Please change file's path strings bellow If you have stored them at location other than bellow.
		//Initializing Test Suite List(TestSuiteList.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestSuiteListExcel = new Read_XLS(System.getProperty("user.dir")+"\\src\\com\\stta\\ExcelFiles\\TestSuiteList.xls");
		//Initializing Test Suite One(SuiteOne.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestCaseListExcelOne = new Read_XLS(System.getProperty("user.dir")+"\\src\\com\\stta\\ExcelFiles\\SuiteOne.xls");
		//Initializing Test Suite Two(SuiteTwo.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestCaseListExcelTwo = new Read_XLS(System.getProperty("user.dir")+"\\src\\com\\stta\\ExcelFiles\\SuiteTwo.xls");
		TestCaseListExcelAPITest = new Read_XLS(System.getProperty("user.dir")+"\\src\\com\\stta\\ExcelFiles\\BasicAPITestSuite.xls");
		TestCaseListExcelUITest = new Read_XLS(System.getProperty("user.dir")+"\\src\\com\\stta\\ExcelFiles\\BasicUIAutoTestSuite.xls");
		//Bellow given syntax will Insert log In applog.log file.
		Add_Log.info("All Excel Files Initialised successfully.");
		
		//Initialize Param.properties file.
		Param = new Properties();
		FileInputStream fip = new FileInputStream(System.getProperty("user.dir")+"//src//com//stta//property//Param.properties");
		Param.load(fip);
		Add_Log.info("Param.properties file loaded successfully.");		
	
		//Initialize Objects.properties file.
		Object = new Properties();
		fip = new FileInputStream(System.getProperty("user.dir")+"//src//com//stta//property//Objects.properties");
		Object.load(fip);
		Add_Log.info("Objects.properties file loaded successfully.");
	}
	public boolean performW(String Keyword,String Object,String ObjectType,String Value) throws Exception{
        System.out.println("");
        try{
        	switch (Keyword.toUpperCase()) {
            case "LOADBROWSER":
                //Loading Browser
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	if(Value.equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser!=null){
    				driver = ExistingmozillaBrowser;
    				break;
    			}else if(Value.equalsIgnoreCase("Chrome") && ExistingchromeBrowser!=null){
    				driver = ExistingchromeBrowser;
    				break;
    			}else if(Value.equalsIgnoreCase("IE") && ExistingIEBrowser!=null){
    				driver = ExistingIEBrowser;
    				break;
    			}
            	
    			if(Value.equalsIgnoreCase("Mozilla")){    				
    				//To Load Firefox driver Instance.
    				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//BrowserDrivers//geckodriver.exe");
    				driver = new FirefoxDriver();
    				ExistingmozillaBrowser=driver;
    				Add_Log.info("Firefox Driver Instance loaded successfully.");
    				
    				
    			}else if(Value.equalsIgnoreCase("Chrome")){
    				//To Load Chrome driver Instance.
    				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//BrowserDrivers//chromedriver.exe");
    				driver = new ChromeDriver();
    				ExistingchromeBrowser=driver;
    				Add_Log.info("Chrome Driver Instance loaded successfully.");
    				
    			}else if(Value.equalsIgnoreCase("IE")){
    				//To Load IE driver Instance.
    				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//BrowserDrivers//IEDriverServer.exe");
    				driver = new InternetExplorerDriver();
    				ExistingIEBrowser=driver;
    				Add_Log.info("IE Driver Instance loaded successfully.");
    				
    			}			
    			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    			driver.manage().window().maximize();
                break;
            case "LOADBROWSERA":
                //Loading Browser
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	if(Value.equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser!=null){
    				driver = ExistingmozillaBrowser;
    				break;
    			}else if(Value.equalsIgnoreCase("Chrome") && ExistingchromeBrowser!=null){
    				driver = ExistingchromeBrowser;
    				break;
    			}else if(Value.equalsIgnoreCase("IE") && ExistingIEBrowser!=null){
    				driver = ExistingIEBrowser;
    				break;
    			}
            	
    			if(Value.equalsIgnoreCase("Mozilla")){
    				// start the proxy
            	    proxy = new BrowserMobProxyServer();
            	    proxy.start(0);
            	    
            	    //get the Selenium proxy object - org.openqa.selenium.Proxy;
            	    Proxy  seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            	    System.out.println(seleniumProxy);
            	    // configure it as a desired capability
            	    DesiredCapabilities capabilities = DesiredCapabilities.firefox();

            	    
            	    ProfilesIni firProfiles = new ProfilesIni();
            	    FirefoxProfile fp = firProfiles.getProfile("certificateIssue"); 
            	    fp.setPreference("network.proxy.http", "192.168.1.20");
            	    fp.setPreference("network.proxy.http_port", proxy.getPort());
            	    fp.setPreference("network.proxy.ssl", "192.168.1.20");
            	    fp.setPreference("network.proxy.ssl_port", proxy.getPort());
            	    fp.setPreference("network.proxy.ftp","192.168.1.20");
            	    fp.setPreference("network.proxy.ftp_port",proxy.getPort());
            	    fp.setPreference("network.proxy.socks","192.168.1.20");
            	    fp.setPreference("network.proxy.socks_port",proxy.getPort());
            	    fp.setPreference("network.proxy.type", 4);
            	    fp.setPreference("network.proxy.no_proxies_on", "192.168.1.20, 127.0.0.1");
//            	    fp.setPreference("network.proxy.share_proxy_settings", true);
            	    fp.setPreference("setAcceptUntrustedCertificates","true");
            	    fp.setPreference("network.http.use-cache", false);
            	    //capabilities.setCapability("marionette", true);
            	    fp.setAcceptUntrustedCertificates(true); 
            		fp.setAssumeUntrustedCertificateIssuer(false);
            		capabilities.setCapability(FirefoxDriver.PROFILE, fp);
//             		capabilities.setCapability (CapabilityType.PROXY, seleniumProxy);
            	    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//           	    capabilities.setCapability(FirefoxDriver.MARIONETTE, false);
            	    capabilities.setCapability("marionette", true);
                    //capabilities.setCapability(FirefoxDriver.MARIONETTE, false);
    				//To Load Firefox driver Instance.
            	    System.setProperty("bmp.allowNativeDnsFallback", "true");
    				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//BrowserDrivers//geckodriver.exe");
    				driver = new FirefoxDriver(capabilities);
    				ExistingmozillaBrowser=driver;
    				Add_Log.info("Firefox Driver Instance loaded successfully.");
    				proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);  		

    			    // create a new HAR with the label "seleniumeasy.com"
    			    proxy.newHar("seleniumeasy.com");
    				
    			}else if(Value.equalsIgnoreCase("Chrome")){
    				//To Load Chrome driver Instance.
    				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//BrowserDrivers//chromedriver.exe");
    				driver = new ChromeDriver();
    				ExistingchromeBrowser=driver;
    				Add_Log.info("Chrome Driver Instance loaded successfully.");
    				
    			}else if(Value.equalsIgnoreCase("IE")){
    				//To Load IE driver Instance.
    				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//BrowserDrivers//IEDriverServer.exe");
    				driver = new InternetExplorerDriver();
    				ExistingIEBrowser=driver;
    				Add_Log.info("IE Driver Instance loaded successfully.");
    				
    			}			
    			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    			driver.manage().window().maximize();
                break;
            case "CLOSEBROWSER":
                //Close Browser
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	//driver.quit();
            	driver.close();
            	System.out.println("closing browser");
        		//null browser Instance when close.
        		ExistingchromeBrowser=null;
        		ExistingmozillaBrowser=null;
        		ExistingIEBrowser=null;
                break;
            case "ENTER":
                //Enter text
                //driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
            	driver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
                break;
            case "LOADURL":
                //Load URL
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	driver.get(Value);
                break;
            case "CLICK":
                //Perform click
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	driver.findElement(this.getObject(Object, ObjectType)).click();
            	//System.out.println("clicked");
                break;
            case "SELECT":
                //Perform select
                //driver.findElement(this.getObject(p,objectName,objectType)).click();
            	Select listbox = new Select(driver.findElement(this.getObject(Object, ObjectType)));
            	listbox.selectByValue(Value);
                break;
            case "WAITFORTEXT":
                //Get text of an element
                //driver.findElement(this.getObject(p,objectName,objectType)).getText();
            	WebDriverWait wait = new WebDriverWait(driver, 50);
            	wait.until(ExpectedConditions.textToBePresentInElementLocated(this.getObject(Object, ObjectType),Value));
                break;
            case "GETTEXT":
                //Get text of an element
                //driver.findElement(this.getObject(p,objectName,objectType)).getText();
                break;
            case "CUSTOM":
                //Get text of an element
                //driver.findElement(this.getObject(p,objectName,objectType)).getText();
//            	driver.switchTo().
                break;
            default:
                break;
            }
        }catch(Exception e){
        	System.out.println(e.toString());
        	return true;
        }
        return false;
        
    }
	public boolean performAndroidM(String Keyword,String Object,String ObjectType,String Value) throws Exception{
        System.out.println("");
        try{
        	switch (Keyword.toUpperCase()) {
        	case "STARTAPPIUM":
                //Enter text
                //driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
            	//driver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
        		String nodePath = Param.getProperty("nodePath");
        		String appiumJSPath = Param.getProperty("appiumJSPath");
        		String appiumLogLoc = Param.getProperty("appiumLogLoc");
        		al = getArrayListFromString(Value);
        		CommandLine command = new CommandLine("cmd");
        		  // Add different arguments In command line which requires to start appium server.
        		  command.addArgument("/c");
        		  command.addArgument(nodePath);
        		  command.addArgument(appiumJSPath);
        		  //Set Server address
        		  command.addArgument("--address");
        		  command.addArgument(al.get(0).toString());
        		  //Set Port
        		  command.addArgument("--port");
        		  command.addArgument(al.get(1).toString());
        		  command.addArgument("--bootstrap-port");
        		  command.addArgument(al.get(2).toString());
        		  command.addArgument("--no-reset");
        		  command.addArgument("--log");
        		  //Set path to store appium server log file.
        		  command.addArgument(appiumLogLoc);
        		  // Execute command line arguments to start appium server.
        		  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        		  DefaultExecutor executor = new DefaultExecutor();
        		  executor.setExitValue(1);
        		  executor.execute(command, resultHandler);
        		  // Wait for 15 minutes so that appium server can start properly before going for test execution.
        		  // Increase this time If face any error.
        		  Thread.sleep(15000);
        		  //System.out.println("appium started");
                break;
        	case "LOADAPP":
                //Enter text
                //driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
            	//driver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
        		DesiredCapabilities capabilities = new DesiredCapabilities();
        		  // Set android deviceName desired capability. Set your device name.
        		  al = getArrayListFromString(Value);
        		  capabilities.setCapability("deviceName", al.get(0).toString());
        		  capabilities.setCapability("udid", al.get(0).toString());
        		  // Set BROWSER_NAME desired capability. It's Android in our case here.
        		  capabilities.setCapability(CapabilityType.BROWSER_NAME, al.get(1).toString());
        		  // Set android VERSION desired capability. Set your mobile device's OS version.
        		  capabilities.setCapability(CapabilityType.VERSION, al.get(2).toString());
        		  // Set android platformName desired capability. It's Android in our case here.
        		  capabilities.setCapability("platformName", al.get(3).toString());
        		  // Set android appPackage desired capability. It is
        		  // com.android.calculator2 for calculator application.
        		  // Set your application's appPackage if you are using any other app.
        		  capabilities.setCapability("appPackage", al.get(4).toString());
        		  // Set android appActivity desired capability. It is
        		  // com.android.calculator2.Calculator for calculator application.
        		  // Set your application's appPackage if you are using any other app.
        		  capabilities.setCapability("appActivity", al.get(5).toString());
        		  capabilities.setCapability("appWaitActivity", al.get(6).toString());
        		  // Created object of RemoteWebDriver will all set capabilities.
        		  // Set appium server address and port number in URL string.
        		  // It will launch calculator app in android device.
        		  driver = new AndroidDriver(new URL(al.get(7).toString()), capabilities);
        		  androidDriver = (AndroidDriver) driver;
        		  androidDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        		  //System.out.println("app loaded");
                break;
        	case "STOPAPP":
                //Enter text
                //driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
            	//driver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
        		//androidDriver.closeApp();
        		//androidDriver.close();
        		//androidDriver.quit();
        		androidDriver =null;
                break;
        	case "CLOSEAPP":
                //Enter text
                //driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
            	//driver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
        		androidDriver.closeApp();
        		//androidDriver.close();
        		androidDriver =null;
                break;
        	case "STOPAPPIUM":
        		stopPort(Value);
        		//stopAppium();
                break;
            case "ENTER":
            	androidDriver.findElement(this.getObject(Object, ObjectType)).clear();
            	androidDriver.findElement(this.getObject(Object, ObjectType)).sendKeys(Value);
                break;
            case "CLICK":
            	androidDriver.findElement(this.getObject(Object, ObjectType)).click();
                break;
            case "WAITFORELEMENT":
        		WebDriverWait wait = new WebDriverWait(driver, 20);
        		wait.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(Object, ObjectType)));
        		/*WebDriverWait wait = new WebDriverWait(driver, 30);
        		wait.until(ExpectedConditions.elementToBeClickable(By
        		        .xpath("//android.widget.Button[contains(@text, 'Log In')]")));
        		or:

        		WebDriverWait wait = new WebDriverWait(driver, 30);
        		wait.until(ExpectedConditions.presenceOfElementLocated(By
        		            .xpath("//android.widget.TextView[contains(@resource-id, 'action_bar_title')]")));*/
        		break;
            case "VERIFYELEMENTPRESENT":
            	iselementpresent = androidDriver.findElements(this.getObject(Object, ObjectType)).size() != 0;
            	return iselementpresent;
        	case "SCROLLTO":
        		androidDriver.scrollTo(Value);
        	case "SWIPELEFT":
        		size = androidDriver.manage().window().getSize();
        		startx = (int) (size.width * 0.80);
        		endx = (int) (size.width * 0.10);
        		starty = size.height / 2;
        		androidDriver.swipe(startx, starty, endx, starty, 3000);
        		Thread.sleep(2000);
                break;
        	case "SWIPERIGHT":
        		size = androidDriver.manage().window().getSize();
        		startx = (int) (size.width * 0.80);
        		endx = (int) (size.width * 0.10);
        		starty = size.height / 2;
        		androidDriver.swipe(endx, starty, startx, starty, 3000);
        		Thread.sleep(2000);
                break;
        	case "SWIPEDOWN":
        		size = androidDriver.manage().window().getSize();
        		starty = (int) (size.height * 0.80);
        		endy = (int) (size.height * 0.20);
        		startx = size.width / 2;
        		androidDriver.swipe(startx, endy, startx, starty, 3000);
        		Thread.sleep(2000);
                break;
        	case "SWIPEUP":
        		size = androidDriver.manage().window().getSize();
        		starty = (int) (size.height * 0.80);
        		endy = (int) (size.height * 0.20);
        		startx = size.width / 2;
        		androidDriver.swipe(startx, starty, startx, endy, 3000);
        		Thread.sleep(2000);
                break;
        	case "VERIFYTEXT":
        		String Actualtext = androidDriver.findElement(this.getObject(Object, ObjectType)).getText();
        		Assert.assertEquals(Actualtext, Value);
        	case "WAITFORTEXT":
        		WebDriverWait wait1 = new WebDriverWait(androidDriver, 20);
            	wait1.until(ExpectedConditions.textToBePresentInElementLocated(this.getObject(Object, ObjectType),Value));
                break;
        	case "DISMISSKEYBOARD":
        		androidDriver.hideKeyboard();
                break;
        	case "SELECT":
        		androidDriver.findElement(this.getObject(Object, ObjectType)).click();
        		androidDriver.scrollTo(Value);
        		androidDriver.findElement(this.getObject(Value,"NAME")).click();
                break;
        	case "SELECTPHOTO":
        		al = getArrayListFromString(Value);
        		if(al.get(0).toString().equalsIgnoreCase("Photos")){
        			androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(0)+"')]", "XPATH")).click();
            		androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(1)+"')]", "XPATH")).click();
            		androidDriver.findElement(this.getObject("//android.view.ViewGroup[contains(@content-desc,'"+al.get(2)+"')]", "XPATH")).click();
        		}else if(al.get(0).toString().equalsIgnoreCase("Gallery")){
        			androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(0)+"')]", "XPATH")).click();
            		androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(1)+"')]", "XPATH")).click();
            		androidDriver.findElement(this.getObject("//android.view.View[@index='"+al.get(2)+"']", "XPATH")).click();
        		}
        		else if(al.get(0).toString().equalsIgnoreCase("Documents")){
        			androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(0)+"')]", "XPATH")).click();
        			androidDriver.findElement(this.getObject("title", "ID")).click();
        			androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(1)+"')]", "XPATH")).click();
        			androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(2)+"')]", "XPATH")).click();
        			//androidDriver.findElement(this.getObject(al.get(2).toString(), "id")).click();
            		androidDriver.findElement(this.getObject("//android.widget.TextView[contains(@text,'"+al.get(3)+"')]", "XPATH")).click();
//            		androidDriver.findElement(this.getObject("//android.widget.LinearLayout[@index='0']/android.widget.GridView[@index='4']/android.widget.FrameLayout[@index='"+al.get(1)+"']", "XPATH")).click();
        		}
        		break;
        	case "SEEKTO":
        		startX = androidDriver.findElement(this.getObject(Object, ObjectType)).getLocation().getX();
        		endX = androidDriver.findElement(this.getObject(Object, ObjectType)).getSize().getWidth();
        		yAxis = androidDriver.findElement(this.getObject(Object, ObjectType)).getLocation().getY();
        		moveToXDirectionAt = (int) (endX * Float.parseFloat(Value));
        		action=new TouchAction(androidDriver);
        		action.press(startX,yAxis).moveTo(moveToXDirectionAt,yAxis).release().perform(); 
                break;
        	case "DRAGDROP":
        		al = getArrayListFromString(Value);
        		action=new TouchAction(androidDriver);
        		action.longPress(androidDriver.findElement(this.getObject(al.get(0).toString(), ObjectType))).moveTo(androidDriver.findElement(this.getObject(al.get(1).toString(), ObjectType))).release().perform();
        		break;
        	case "LANDSCAPE":
        		androidDriver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
        	case "PORTRAIT":
        		androidDriver.rotate(org.openqa.selenium.ScreenOrientation.PORTRAIT);
        		break;
        	case "LONGPRESS":
        		action=new TouchAction(androidDriver);
        		action.longPress(androidDriver.findElement(this.getObject(Object, ObjectType))).perform();
        		break;
            default:
                break;
            }
        }catch(Exception e){
        	return true;
        }
        return false;
        
    }
	public ArrayList getArrayListFromString(String string){
		/*String s = "a;b;3";
    	ArrayList al = getArrayListFromString(s);
    	for(int i=0;i<=al.size();i++){
    		System.out.println(al.get(i) );
    	}
    	
    		Iterator itr = al.iterator();
    		System.out.println("ArrayList items are : ");
    		while (itr.hasNext()) {
    	   	System.out.println(itr.next()+"\n");
    	}*/
		ArrayList<String> alist = new ArrayList<String>(Arrays.asList(string.split(";")));
		return alist;
	}
	public void stopAppium() throws ExecuteException, IOException{
		CommandLine command = new CommandLine("cmd");
		  command.addArgument("/c");
		  command.addArgument("taskkill");
		  command.addArgument("/F");
		  command.addArgument("/IM");
		  command.addArgument("node.exe");
		  // Execute command line arguments to stop appium server.
		  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		  DefaultExecutor executor = new DefaultExecutor();
		  executor.setExitValue(1);
		  executor.execute(command, resultHandler);
		  //System.out.println("appium stopped");
	}
	public void getScreenDimensions(){
		Dimension size;
		size = androidDriver.manage().window().getSize();
		int startx = (int) (size.width * 0.70);
		int endx = (int) (size.width * 0.30);
		int starty = size.height / 2;
	}
	public void stopPort(String appiumNode) throws java.io.IOException {
			al = getArrayListFromString(appiumNode);
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""+al.get(1).toString()+"\"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)");
			rt.exec("cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""+al.get(2).toString()+"\"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)");
//			Process proc = rt.exec("cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""+port+"\"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)");

			/*BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}
			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
			}*/
    }
	/**
     * Find element BY using object type and value
     * @param p
     * @param objectName
     * @param objectType
     * @return
     * @throws Exception
     */
    private By getObject(String Object,String ObjectType) throws Exception{
        //Find by xpath
        /*if(objectType.equalsIgnoreCase("XPATH")){
            
            return By.xpath(p.getProperty(objectName));
        }
        //find by class
        else if(objectType.equalsIgnoreCase("CLASSNAME")){
            
            return By.className(p.getProperty(objectName));
            
        }
        //find by name
        else if(objectType.equalsIgnoreCase("NAME")){
            
            return By.name(p.getProperty(objectName));
            
        }
        //Find by css
        else if(objectType.equalsIgnoreCase("CSS")){
            
            return By.cssSelector(p.getProperty(objectName));
            
        }
        //find by link
        else if(objectType.equalsIgnoreCase("LINK")){
            
            return By.linkText(p.getProperty(objectName));
            
        }
        //find by partial link
        else if(objectType.equalsIgnoreCase("PARTIALLINK")){
            
            return By.partialLinkText(p.getProperty(objectName));
            
        }else
        {
            throw new Exception("Wrong object type");
        }*/
    	if(ObjectType.equalsIgnoreCase("XPATH")){
            
            return By.xpath(Object);
        }
        //find by class
        else if(ObjectType.equalsIgnoreCase("CLASSNAME")){
            
            return By.className(Object);
            
        }
        else if(ObjectType.equalsIgnoreCase("ID")){
            
            return By.id(Object);
            
        }
        //find by name
        else if(ObjectType.equalsIgnoreCase("NAME")){
            
            return By.name(Object);
            
        }
        //Find by css
        else if(ObjectType.equalsIgnoreCase("CSS")){
            
            return By.cssSelector(Object);
            
        }
        //find by links
        else if(ObjectType.equalsIgnoreCase("LINK")){
            
            return By.linkText(Object);
            
        }
        //find by partial link
        else if(ObjectType.equalsIgnoreCase("PARTIALLINK")){
            
            return By.partialLinkText(Object);
            
        }else
        {
            throw new Exception("Wrong object type");
        }
    }
	public void loadWebBrowser(){
		//Check If any previous webdriver browser Instance Is exist then run new test In that existing webdriver browser Instance.
			if(Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser!=null){
				driver = ExistingmozillaBrowser;
				return;
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("chrome") && ExistingchromeBrowser!=null){
				driver = ExistingchromeBrowser;
				return;
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingIEBrowser!=null){
				driver = ExistingIEBrowser;
				return;
			}		
		
		
			if(Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla")){
				//To Load Firefox driver Instance.
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//BrowserDrivers//geckodriver.exe");
				driver = new FirefoxDriver();
				ExistingmozillaBrowser=driver;
				Add_Log.info("Firefox Driver Instance loaded successfully.");
				
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("Chrome")){
				//To Load Chrome driver Instance.
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//BrowserDrivers//chromedriver.exe");
				driver = new ChromeDriver();
				ExistingchromeBrowser=driver;
				Add_Log.info("Chrome Driver Instance loaded successfully.");
				
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("IE")){
				//To Load IE driver Instance.
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//BrowserDrivers//IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				ExistingIEBrowser=driver;
				Add_Log.info("IE Driver Instance loaded successfully.");
				
			}			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();			
	}
	
	public void closeWebBrowser(){
		driver.close();
		//null browser Instance when close.
		ExistingchromeBrowser=null;
		ExistingmozillaBrowser=null;
		ExistingIEBrowser=null;
	}
	
	//getElementByXPath function for static xpath
	public WebElement getElementByXPath(String Key){
		try{
			//This block will find element using Key value from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key)));
		}catch(Throwable t){
			//If element not found on page then It will return null.
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//getElementByXPath function for dynamic xpath
	public WebElement getElementByXPath(String Key1, int val, String key2){
		try{
			//This block will find element using values of Key1, val and key2 from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key1)+val+Object.getProperty(key2)));
		}catch(Throwable t){
			//If element not found on page then It will return null.
			Add_Log.debug("Object not found for custom xpath");
			return null;
		}
	}
	
	//Call this function to locate element by ID locator.
	public WebElement getElementByID(String Key){
		try{
			return driver.findElement(By.id(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by Name Locator.
	public WebElement getElementByName(String Key){
		try{
			return driver.findElement(By.name(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by cssSelector Locator.
	public WebElement getElementByCSS(String Key){
		try{
			return driver.findElement(By.cssSelector(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by ClassName Locator.
	public WebElement getElementByClass(String Key){
		try{
			return driver.findElement(By.className(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by tagName Locator.
	public WebElement getElementByTagName(String Key){
		try{
			return driver.findElement(By.tagName(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by link text Locator.
	public WebElement getElementBylinkText(String Key){
		try{
			return driver.findElement(By.linkText(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by partial link text Locator.
	public WebElement getElementBypLinkText(String Key){
		try{
			return driver.findElement(By.partialLinkText(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
}
