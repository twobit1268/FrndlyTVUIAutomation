package com.frndlytv.pages;

import com.frndlytv.utils.ConfigReader;
import com.frndlytv.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait")));
        PageFactory.initElements(driver, this);
    }

    protected void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
    }

    protected void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void clickElement(WebElement element) {
        waitForElementClickable(element);
        element.click();
        logger.info("Clicked on element: {}", element.toString());
    }

    protected void sendKeys(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' into element: {}", text, element.toString());
    }

    protected String getText(WebElement element) {
        waitForElementVisible(element);
        return element.getText();
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected Object executeJavaScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }

    protected void waitForPageLoad() {
        wait.until(webDriver -> executeJavaScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
