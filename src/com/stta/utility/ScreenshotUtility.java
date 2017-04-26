//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.stta.utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.stta.TestSuiteBase.SuiteBase;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class ScreenshotUtility implements ITestListener {
	ATUTestRecorder recorder;

	// This method will execute before starting of Test suite.
	public void onStart(ITestContext tr) {
		if (SuiteBase.Param.getProperty("recordVideo").equalsIgnoreCase("yes")) {
			try {
				recordVideo(tr);
			} catch (ATUTestRecorderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// This method will execute, Once the Test suite is finished.
	public void onFinish(ITestContext tr) {
		if (SuiteBase.Param.getProperty("recordVideo").equalsIgnoreCase("yes")) {
			try {
				stopVideoRecord();
			} catch (ATUTestRecorderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// This method will execute only when the test is pass.
	public void onTestSuccess(ITestResult tr) {
		// If screenShotOnPass = yes, call captureScreenShot.
		if (SuiteBase.Param.getProperty("screenShotOnPass").equalsIgnoreCase("yes")) {
			captureScreenShot(tr, "pass");
		}
	}

	// This method will execute only on the event of fail test.
	public void onTestFailure(ITestResult tr) {
		// If screenShotOnFail = yes, call captureScreenShot.
		if (SuiteBase.Param.getProperty("screenShotOnFail").equalsIgnoreCase("yes")) {
			captureScreenShot(tr, "fail");
		}
	}

	// This method will execute before the main test start (@Test)
	public void onTestStart(ITestResult tr) {

	}

	// This method will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult tr) {
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
	}

	// Function to capture screenshot.
	public void captureScreenShot(ITestResult result, String status) {

		String destDir = "";
		String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "."
				+ result.getMethod().getMethodName();
		// To capture screenshot.
		if (SuiteBase.driver == null)
			return;
		File scrFile = ((TakesScreenshot) SuiteBase.driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// If status = fail then set folder name "screenshots/Failures"
		if (status.equalsIgnoreCase("fail")) {
			destDir = "screenshots/Failures";
		}
		// If status = pass then set folder name "screenshots/Success"
		else if (status.equalsIgnoreCase("pass")) {
			destDir = "screenshots/Success";
		}

		// To create folder to store screenshots
		new File(destDir).mkdirs();
		// Set file name with combination of test class name + date time.
		String destFile = passfailMethod + " - " + dateFormat.format(new Date()) + ".png";
		// Store file at destination folder location
		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void recordVideo(ITestContext result) throws ATUTestRecorderException {

		String destDir = "ScriptVideos";
		new File(destDir).mkdirs();
		String videoName = result.getName();
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		recorder = new ATUTestRecorder(destDir, videoName + dateFormat.format(date), false);
		recorder.start();

	}

	private void stopVideoRecord() throws ATUTestRecorderException {
		// TODO Auto-generated method stub
		recorder.stop();
	}
}