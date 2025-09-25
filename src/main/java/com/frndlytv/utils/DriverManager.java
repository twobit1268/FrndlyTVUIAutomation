package com.frndlytv.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(String browserName) {
        WebDriver driver = createDriver(browserName);
        driverThreadLocal.set(driver);

        // Configure timeouts
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getIntProperty("implicit.wait")))
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getIntProperty("page.load.timeout")));

        // Maximize window
        driver.manage().window().maximize();

        logger.info("WebDriver initialized successfully for browser: {}", browserName);
    }

    private static WebDriver createDriver(String browserName) {
        WebDriver driver;
        boolean headless = ConfigReader.getBooleanProperty("headless");

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call setDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("WebDriver session ended successfully");
        }
    }
}