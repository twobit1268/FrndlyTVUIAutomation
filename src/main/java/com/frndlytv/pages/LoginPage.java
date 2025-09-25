package com.frndlytv.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    String url = com.frndlytv.utils.ConfigReader.getProperty("settings.url");

    // Page Elements
    @FindBy(xpath = "//a[@class='nav-link-2']")
    private WebElement signInButton;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[contains(@formcontrolname, 'password')]")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit' or contains(@class, 'login') or contains(text(), 'Sign In')]")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'error') or contains(@class, 'alert')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[contains(text(), 'Forgot') or contains(text(), 'forgot')]")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//h1 | //h2 | //div[contains(@class, 'title')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//img[@routerlink='/settings']")
    private WebElement settingsButton;

    @FindBy(xpath = "//button[@class='btn btn-info']")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[@id='slick-slide-control20']" )
    private WebElement sliderButton;

    @FindBy(xpath = "//ott-banner//button[contains(text(), 'Play') or contains(text(), 'Watch')]")
    private WebElement playButton;

    @FindBy(xpath = "/html/body/ott-app/div/div/div[2]/home-view/div[1]/div[1]/ott-banner/div/div/section/div/div/div[3]/a/div[2]/div[2]/button")
    private WebElement startVideo;
    // Page Actions
    public void clickSignLoginButton() {
        waitForElementClickable(signInButton);
        clickElement(signInButton);
        logger.info("Clicked login button");
    }
    public void navigateToLoginPage() {
        String loginUrl = com.frndlytv.utils.ConfigReader.getProperty("login.url");
        navigateTo(loginUrl);
        waitForPageLoad();
        logger.info("Navigated to login page: {}", loginUrl);
    }

    public void enterEmail(String email) {
        waitForElementVisible(emailField);
        sendKeys(emailField, email);
        logger.info("Entered email: {}", email);
    }

    public void enterPassword(String password) {
        waitForElementVisible(passwordField);
        sendKeys(passwordField, password);
        logger.info("Entered password");
    }

    public void clickLoginButton() {
        waitForElementClickable(loginButton);
        clickElement(loginButton);
        logger.info("Clicked login button");
    }
    public void clickFrontVideo() {
        waitForElementClickable(playButton);
        clickElement(playButton);
        logger.info("Clicked play button");
    }

    public HomePage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();

        // Wait for navigation to complete
        waitForPageLoad();

        logger.info("Login attempted with email: {}", email);
        return new HomePage();
    }

    public HomePage loginWithDefaultCredentials() {
        String email = com.frndlytv.utils.ConfigReader.getProperty("test.email");
        String password = com.frndlytv.utils.ConfigReader.getProperty("test.password");
        return login(email, password);
    }
    public void clickSliderButton() {
        //waitForElementClickable(sliderButton);
        clickElement(sliderButton);
    }
    public void clickVisibleVideo() {
        waitForElementClickable(playButton);
        clickElement(playButton);
    }
    public void clickStartVideo() {
        waitForElementClickable(startVideo);
        clickElement(startVideo);
    }
    public void clickSettingsButton() {
       // waitForElementClickable(settingsButton);
        logger.info("Clicked settings button");
        logger.info("Clicked settings button: {}", url);
        navigateTo(url);
        waitForPageLoad();
        logger.info("Clicked settings button");
    }
    public void clickLogoutButton() {
        waitForElementClickable(logoutButton);
        clickElement(logoutButton);
        logger.info("Clicked logout button");

    }

    public boolean isErrorMessageDisplayed() {
        try {
            return isElementVisible(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return getText(errorMessage);
        }
        return "";
    }

    public boolean isLoginPageLoaded() {
        try {
            waitForElementVisible(emailField);
            waitForElementVisible(passwordField);
            waitForElementVisible(loginButton);
            return true;
        } catch (Exception e) {
            logger.error("Login page not loaded properly", e);
            return false;
        }
    }

    public void clickForgotPassword() {
        if (isElementVisible(forgotPasswordLink)) {
            clickElement(forgotPasswordLink);
            logger.info("Clicked forgot password link");
        }
    }

    public String getPageTitle() {
        try {
            return getText(pageTitle);
        } catch (Exception e) {
            return super.getPageTitle();
        }
    }
}

