package com.frndlytv.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String screenshotDir = ConfigReader.getProperty("screenshot.directory");

            File destFile = new File(screenshotDir + "/" + fileName);
            destFile.getParentFile().mkdirs();

            FileUtils.copyFile(sourceFile, destFile);

            logger.info("Screenshot captured: {}", destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot for test: {}", testName, e);
            return null;
        }
    }
}
