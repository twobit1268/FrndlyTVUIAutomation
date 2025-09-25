package com.frndlytv.tests;

import com.frndlytv.pages.HomePage;
import com.frndlytv.pages.LoginPage;
import com.frndlytv.utils.ConfigReader;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        logger.info("Starting testValidLogin");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.clickSignLoginButton();

        // Verify login page loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(),
                "Login page should be loaded successfully");

        // Perform login
        HomePage homePage = loginPage.loginWithDefaultCredentials();

        // Verify successful login
        Assert.assertTrue(homePage.isUserLoggedIn(),
                "User should be logged in successfully");

        Assert.assertTrue(homePage.isHomePageLoaded(),
                "Home page should be loaded after login");

        logger.info("testValidLogin completed successfully");
    }

    @Test(priority = 2, description = "Verify login with invalid credentials shows error")
    public void testInvalidLogin() {
        logger.info("Starting testInvalidLogin");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();

        // Attempt login with invalid credentials
        loginPage.login("invalid@email.com", "wrongpassword");

        // Verify error is shown (if error handling is implemented)
        // Note: This might need adjustment based on actual error handling
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login") || loginPage.isErrorMessageDisplayed(),
                "Should remain on login page or show error message for invalid credentials");

        logger.info("testInvalidLogin completed successfully");
    }

    @Test(priority = 3, description = "Verify login with empty credentials")
    public void testEmptyCredentialsLogin() {
        logger.info("Starting testEmptyCredentialsLogin");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();

        // Attempt login with empty credentials
        loginPage.login("", "");

        // Verify user is not logged in
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"),
                "Should remain on login page with empty credentials");

        logger.info("testEmptyCredentialsLogin completed successfully");
    }

    @Test(priority = 4, description = "Verify home page elements after successful login")
    public void testHomePageElements() {
        logger.info("Starting testHomePageElements");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.clickSignLoginButton();

        HomePage homePage = loginPage.loginWithDefaultCredentials();

        // Verify user is logged in
        Assert.assertTrue(homePage.isUserLoggedIn(),
                "User should be logged in");

        // Verify navigation elements (adjust based on actual page structure)
        Assert.assertTrue(homePage.isNavigationMenuVisible() ||
                        homePage.isChannelGuideVisible() ||
                        homePage.hasContentLoaded(),
                "Home page should display navigation or content elements");

        logger.info("testHomePageElements completed successfully");
    }

    @Test(priority = 5, description = "Validate slider")
    public void testSliderButton() {
        logger.info("Starting testSliderButton");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.clickSignLoginButton();
        HomePage homePage = loginPage.loginWithDefaultCredentials();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page should be loaded after login");
        logger.info("testSliderButton completed successfully");
        loginPage.clickSettingsButton();
        loginPage.clickLogoutButton();
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should be loaded after login");
        logger.info("testSliderButton completed successfully");




    }
}

