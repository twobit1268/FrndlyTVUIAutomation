package com.frndlytv.tests;

import com.frndlytv.utils.ConfigReader;
import com.frndlytv.utils.DriverManager;
import com.frndlytv.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        String browser = System.getProperty("browser", ConfigReader.getProperty("browser"));
        DriverManager.setDriver(browser);
        logger.info("Test setup completed with browser: {}", browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            if (ConfigReader.getBooleanProperty("screenshot.on.failure")) {
                String screenshotPath = ScreenshotUtil.captureScreenshot(
                        DriverManager.getDriver(),
                        result.getMethod().getMethodName()
                );
                logger.info("Screenshot captured for failed test: {}", screenshotPath);
            }
        }

        DriverManager.quitDriver();
        logger.info("Test teardown completed");
    }

    @BeforeClass
    public void beforeClass() {
        logger.info("Starting test class: {}", this.getClass().getSimpleName());
    }

    @AfterClass
    public void afterClass() {
        logger.info("Completed test class: {}", this.getClass().getSimpleName());
    }
}
