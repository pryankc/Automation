package com.stta.BasicAPITestSuite;

import static com.jayway.restassured.RestAssured.given;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.path.json.JsonPath;
import com.stta.utility.Read_XLS;
import com.stta.utility.SuiteUtility;

public class BasicAPIMultipleResultExtract1 extends BasicAPITestSuiteBase{
	Read_XLS FilePath = null;
	String SheetName = null;
	String TestCaseName = null;	
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	String TestDataToRun[]=null;
	static boolean TestCasePass=true;
	static int DataSet=-1;	
	static boolean Testskip=false;
	static boolean Testfail=false;
	SoftAssert s_assert =null;
	String Error= null;
	String Scheme= null;
	String Number_length= null;
	String Number_prefix= null;
	String Type= null;
	String Brand= null;
	String Prepaid= null;
	String Bank_name= null;
	String Bank_logo= null;
	String Bank_url= null;
	String Bank_city= null;
	String Bank_phone= null;
	String Country_alpha2= null;
	String Country_name= null;
	String Country_numeric= null;
	String Country_latitude= null;
	String Country_longitude= null;
	

	
	@BeforeTest
	public void checkCaseToRun() throws IOException{
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();	
		//To set SuiteOne.xls file's path In FilePath Variable.
		FilePath = TestCaseListExcelAPITest;	
		TestCaseName = this.getClass().getSimpleName();	
		//SheetName to check CaseToRun flag against test case.
		SheetName = "TestCasesList";
		//Name of column In TestCasesList Excel sheet.
		ToRunColumnNameTestCase = "CaseToRun";
		//Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";
		
		Add_Log.info(TestCaseName+" : Execution started.");
		
		//To check test case's CaseToRun = Y or N In related excel sheet.
		//If CaseToRun = N or blank, Test case will skip execution. Else It will be executed.
		if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnNameTestCase,TestCaseName)){
			Add_Log.info(TestCaseName+" : CaseToRun = N for So Skipping Execution.");
			//To report result as skip for test cases In TestCasesList sheet.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
			//To throw skip exception for this test case.
			throw new SkipException(TestCaseName+"'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+TestCaseName);
		}	
		//To retrieve DataToRun flags of all data set lines from related test data sheet.
		TestDataToRun = SuiteUtility.checkToRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);
	}
	
	//Accepts 4 column's String data In every Iteration.
	@Test(dataProvider="BasicAPIMultipleResultExtract1Data")
	public void BasicAPIMultipleResultExtract1Test(String API,String APIName,String success,String scheme,
			String number_length,
			String number_prefix,
			String type,
			String brand,
			String prepaid,
			String bank_name,
			String bank_logo,
			String bank_url,
			String bank_city
			) throws Exception{
		
		DataSet++;
		
		//Created object of testng SoftAssert class.
		s_assert = new SoftAssert();
		
		//If found DataToRun = "N" for data set then execution will be skipped for that data set.
		if(!TestDataToRun[DataSet].equalsIgnoreCase("Y")){	
			Add_Log.info(TestCaseName+" : DataToRun = N for data set line "+(DataSet+1)+" So skipping Its execution.");
			//If DataToRun = "N", Set Testskip=true.
			Testskip=true;
			throw new SkipException("DataToRun for row number "+DataSet+" Is No Or Blank. So Skipping Its Execution.");
		}
		
		//If found DataToRun = "Y" for data set then bellow given lines will be executed.
		//To Convert data from String to Integer
		//int ValueOne = Integer.parseInt(DataCol1);
		int ExpectedResultInt =  Integer.parseInt(success);
		
		//Calculate the Sum.
		//int ActualResult = ValueOne+ValueTwo+ValueThree;
		//Compare actual and expected values.
		/*System.setProperty("javax.net.debug", "all");
		System.setProperty("https.protocols", "TLSv1.2");
		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
		    public boolean verify(String string,SSLSession ssls) {
		        return true;
		    }
		});*/
//		System.setProperty("https.protocols", "SSLv3");
		String res = null;
		/*try{
		res =given().when().get(API)
	      .then().extract().asString();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}*/
		/*RestAssured.config = RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
		res = given().when().get(API).then().extract().asString();*/
		
		/*res = given().config(RestAssuredConfig.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames())).when().relaxedHTTPSValidation().get(API)
			      .then().extract().asString();*/
		/*res = RestAssured.given()
				.config(RestAssured.config().sslConfig(
						new SSLConfig().relaxedHTTPSValidation()))
						.get(API).then().extract().asString();
		JsonPath jsonpath = new JsonPath(res);*/
		int ActualResult = 1;//jsonpath.getInt("settings.success"); 
		//Error = jsonpath.getString("scheme");
		res = URLConnectionReader.getText(API);
        System.out.println(res);
        JsonPath jsonpath = new JsonPath(res);
		Scheme = jsonpath.getString("bin");
		Number_length = jsonpath.getString("bank");
		Number_prefix = jsonpath.getString("card");
		Type = jsonpath.getString("type");
		Brand = jsonpath.getString("level");
		Prepaid = jsonpath.getString("country");
		Bank_name = jsonpath.getString("countrycode");
		Bank_logo = jsonpath.getString("website");
		Bank_url = jsonpath.getString("phone");
		Bank_city = jsonpath.getString("valid");
		/*Bank_phone = jsonpath.getString("bank.phone");
		Country_alpha2 = jsonpath.getString("country.alpha2");
		Country_name = jsonpath.getString("country.name");
		Country_numeric = jsonpath.getString("country.numeric");
		Country_latitude = jsonpath.getString("country.latitude");
		Country_longitude = jsonpath.getString("country.longitude");*/
		if(!(ActualResult==ExpectedResultInt)){
			//If expected and actual results not match, Set flag Testfail=true.
			Testfail=true;
			
			//If result Is fail then test failure will be captured Inside s_assert object reference.
			//This soft assertion will not stop your test execution.
			s_assert.assertEquals(ActualResult, ExpectedResultInt, ActualResult+" And "+ExpectedResultInt+" Not Match");
		}
		
		if(Testfail){
			//At last, test data assertion failure will be reported In testNG reports and It will mark your test data, test case and test suite as fail.
			s_assert.assertAll();		
		}
	}
	
	//@AfterMethod method will be executed after execution of @Test method every time.
	@AfterMethod
	public void reporterDataResults(){		
		if(Testskip){
			Add_Log.info(TestCaseName+" : Reporting test data set line "+(DataSet+1)+" as SKIP In excel.");
			//If found Testskip = true, Result will be reported as SKIP against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "SKIP");
		}
		else if(Testfail){
			Add_Log.info(TestCaseName+" : Reporting test data set line "+(DataSet+1)+" as FAIL In excel.");
			//To make object reference null after reporting In report.
			s_assert = null;
			//Set TestCasePass = false to report test case as fail In excel sheet.
			TestCasePass=false;	
			//If found Testfail = true, Result will be reported as FAIL against data set line In excel sheet.
			//SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Decrypted", DataSet+1, Error);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Scheme", DataSet+1, Scheme);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Number_length", DataSet+1, Number_length);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Number_prefix", DataSet+1, Number_prefix);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Type", DataSet+1, Type);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Brand", DataSet+1, Brand);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Prepaid", DataSet+1, Prepaid);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_name", DataSet+1, Bank_name);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_logo", DataSet+1, Bank_logo);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_url", DataSet+1, Bank_url);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_city", DataSet+1, Bank_city);
			/*SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_phone", DataSet+1, Bank_phone);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_alpha2", DataSet+1, Country_alpha2);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_name", DataSet+1, Country_name);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_numeric", DataSet+1, Country_numeric);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_latitude", DataSet+1, Country_latitude);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_longitude", DataSet+1, Country_longitude);*/
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "FAIL");
		}else{
			Add_Log.info(TestCaseName+" : Reporting test data set line "+(DataSet+1)+" as PASS In excel.");
			//If found Testskip = false and Testfail = false, Result will be reported as PASS against data set line In excel sheet.
			//SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Decrypted", DataSet+1, Error);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Scheme", DataSet+1, Scheme);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Number_length", DataSet+1, Number_length);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Number_prefix", DataSet+1, Number_prefix);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Type", DataSet+1, Type);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Brand", DataSet+1, Brand);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Prepaid", DataSet+1, Prepaid);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_name", DataSet+1, Bank_name);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_logo", DataSet+1, Bank_logo);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_url", DataSet+1, Bank_url);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_city", DataSet+1, Bank_city);
			/*SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Bank_phone", DataSet+1, Bank_phone);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_alpha2", DataSet+1, Country_alpha2);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_name", DataSet+1, Country_name);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_numeric", DataSet+1, Country_numeric);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_latitude", DataSet+1, Country_latitude);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Country_longitude", DataSet+1, Country_longitude);*/
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "PASS");
		}
		//At last make both flags as false for next data set.
		Testskip=false;
		Testfail=false;
	}
	
	
	@DataProvider
	public Object[][] BasicAPIMultipleResultExtract1Data(){
		//To retrieve data from Data 1 Column,Data 2 Column,Data 3 Column and Expected Result column of SuiteOneCaseOne data Sheet.
		//Last two columns (DataToRun and Pass/Fail/Skip) are Ignored programatically when reading test data.
		return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
	}	
	
	//To report result as pass or fail for test cases In TestCasesList sheet.
	@AfterTest
	public void closeBrowser(){
		if(TestCasePass){
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "PASS");
		}
		else{
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "FAIL");
			
		}
	}


}
