package com.stta.BasicUIAutoTestSuite;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.stta.utility.Read_XLS;
import com.stta.utility.SuiteUtility;

public class BasicUIAutoTestSuiteCaseM1 extends BasicUIAutoTestSuiteBase{
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
	String Error = null;
	long startTime = 0;
	long stopTime = 0;
	static String appiumNode,Caps;
	public boolean cloud;
	@BeforeTest
	public void checkCaseToRun() throws IOException{
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();	
		//To set SuiteOne.xls file's path In FilePath Variable.
		FilePath = TestCaseListExcelUITest;	
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
		//appiumNode = appiumnode;
		//Caps = caps;
		//stopAppium();
	}
	@Test(priority=1)
	@Parameters ({"appiumnode","caps"})
	public void cloudData(String appiumnode,String caps){
		appiumNode = appiumnode;
		Caps = caps;
		al = getArrayListFromString(appiumNode);
		cloud = true;
	}
	//Accepts 4 column's String data In every Iteration.
	@Test(dataProvider="BasicUIAutoTestSuiteCaseM1Data", priority = 2)
	public void BasicUIAutoTestSuiteCaseM1Test(String Keyword,String Object,String ObjectType,String Value,String RunTime) throws Exception{
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
		
		long startTime = System.nanoTime();
		if((Keyword.equalsIgnoreCase("STARTAPPIUM") || Keyword.equalsIgnoreCase("STOPAPPIUM")) && Value.equalsIgnoreCase("PARALLEL")){
			Testfail = performAndroidM(Keyword,Object,ObjectType,appiumNode);
		}else if((Keyword.equalsIgnoreCase("LOADAPP") && Value.equalsIgnoreCase("PARALLEL"))){
			Testfail = performAndroidM(Keyword,Object,ObjectType,Caps);
		}else{
			Testfail = performAndroidM(Keyword,Object,ObjectType,Value);
		}
		//Testfail = performAndroidM(Keyword,Object,ObjectType,Value);
		long stopTime = System.nanoTime();
		SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "RunTime", DataSet+1, ((double)(stopTime - startTime)/1000000000) + " secs");
		//System.out.println(((double)(stopTime - startTime)/1000000000) + " secs");
		/*//If found DataToRun = "Y" for data set then bellow given lines will be executed.
		//To Convert data from String to Integer
		//int ValueOne = Integer.parseInt(DataCol1);
		int ExpectedResultInt =  Integer.parseInt(success);
		
		//Calculate the Sum.
		//int ActualResult = ValueOne+ValueTwo+ValueThree;
		//Compare actual and expected values.
		String res =given().when().get(API)
	      .then().contentType("application/json").statusCode(200).extract().asString();
		JsonPath jsonpath = new JsonPath(res);
		int ActualResult = jsonpath.getInt("settings.success"); 
		Error = jsonpath.getString("settings.error");
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
		}*/
		if(Testfail){
			//If expected and actual results not match, Set flag Testfail=true.
			//Testfail=true;
			
			//If result Is fail then test failure will be captured Inside s_assert object reference.
			//This soft assertion will not stop your test execution.
			s_assert.assertFalse(Testfail);
		}
		if(Testfail){
			//At last, test data assertion failure will be reported In testNG reports and It will mark your test data, test case and test suite as fail.
			s_assert.assertAll();		
		}
		
		
		
		
	}
	
	//@AfterMethod method will be executed after execution of @Test method every time.
	@AfterMethod
	public void reporterDataResults(){
		if (cloud){
			cloud = false;
			return;
		}else if(Testskip){
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
			//SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Error", DataSet+1, Error);
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "FAIL");
		}else{
			Add_Log.info(TestCaseName+" : Reporting test data set line "+(DataSet+1)+" as PASS In excel.");
			//If found Testskip = false and Testfail = false, Result will be reported as PASS against data set line In excel sheet.
			SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet+1, "PASS");
		}
		//At last make both flags as false for next data set.
		Testskip=false;
		Testfail=false;
	}
	
	
	@DataProvider
	public Object[][] BasicUIAutoTestSuiteCaseM1Data(){
		//To retrieve data from Data 1 Column,Data 2 Column,Data 3 Column and Expected Result column of SuiteOneCaseOne data Sheet.
		//Last two columns (DataToRun and Pass/Fail/Skip) are Ignored programatically when reading test data.
		return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
	}	
	
	//To report result as pass or fail for test cases In TestCasesList sheet.
	@AfterTest
	public void closeBrowser() throws Exception{
		DataSet=-1;
		//performAndroidM("STOPAPPIUM","","","");
		if(TestCasePass){
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "PASS");
		}
		else{
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "FAIL");
			
		}
	}
	/*@AfterClass
	public void closeAppAndAppium() throws Exception{
		performAndroidM("STOPAPPIUM","","","");
	}*/
	
}
