package com.frndlytv.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import java.util.List;

public class HomePage extends BasePage {

    // Page Elements
    @FindBy(xpath = "//div[contains(@class, 'user') or contains(@class, 'profile')] | //span[contains(@class, 'username')]")
    private WebElement userProfile;

    @FindBy(xpath = "//button[contains(text(), 'Logout') or contains(text(), 'Sign Out')] | //a[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//nav | //div[contains(@class, 'navigation')] | //ul[contains(@class, 'menu')]")
    private WebElement navigationMenu;

    @FindBy(xpath = "//div[contains(@class, 'channel')] | //div[contains(@class, 'guide')]")
    private WebElement channelGuide;

    @FindBy(xpath = "//div[contains(@class, 'video')] | //div[contains(@class, 'player')]")
    private WebElement videoPlayer;

    @FindBy(xpath = "//input[@type='search'] | //input[contains(@placeholder, 'search')]")
    private WebElement searchBox;

    @FindBy(xpath = "//div[contains(@class, 'content')] | //div[contains(@class, 'shows')] | //div[contains(@class, 'movies')]")
    private List<WebElement> contentItems;

    @FindBy(xpath = "//h1 | //h2 | //div[contains(@class, 'welcome')] | //div[contains(@class, 'title')]")
    private WebElement welcomeMessage;

    // Page Actions
    public boolean isUserLoggedIn() {
        try {
            // Check for multiple indicators of successful login
            return isElementVisible(userProfile) ||
                    isElementVisible(logoutButton) ||
                    isElementVisible(navigationMenu) ||
                    getCurrentUrl().contains("dashboard") ||
                    getCurrentUrl().contains("home") ||
                    !getCurrentUrl().contains("login");
        } catch (Exception e) {
            logger.error("Error checking login status", e);
            return false;
        }
    }

    public boolean isHomePageLoaded() {
        try {
            waitForPageLoad();
            return isUserLoggedIn();
        } catch (Exception e) {
            logger.error("Home page not loaded properly", e);
            return false;
        }
    }

    public boolean isChannelGuideVisible() {
        try {
            return isElementVisible(channelGuide);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVideoPlayerVisible() {
        try {
            return isElementVisible(videoPlayer);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNavigationMenuVisible() {
        try {
            return isElementVisible(navigationMenu);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        if (isElementVisible(logoutButton)) {
            clickElement(logoutButton);
            logger.info("Clicked logout button");
        } else {
            logger.warn("Logout button not found or not visible");
        }
    }

    public void searchContent(String searchTerm) {
        if (isElementVisible(searchBox)) {
            sendKeys(searchBox, searchTerm);
            logger.info("Searched for: {}", searchTerm);
        }
    }

    public int getContentItemsCount() {
        return contentItems.size();
    }

    public String getWelcomeMessage() {
        try {
            if (isElementVisible(welcomeMessage)) {
                return getText(welcomeMessage);
            }
        } catch (Exception e) {
            logger.debug("Welcome message not found", e);
        }
        return "";
    }

    public boolean hasContentLoaded() {
        return getContentItemsCount() > 0;
    }

    public void navigateToChannelGuide() {
        if (isElementVisible(channelGuide)) {
            clickElement(channelGuide);
            logger.info("Navigated to channel guide");
        }
    }
}

