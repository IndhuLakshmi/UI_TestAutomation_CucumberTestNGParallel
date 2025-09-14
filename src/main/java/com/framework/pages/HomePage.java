package com.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sample Page Object using PageFactory.
 */
public class HomePage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement heading;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getHeading() {
        return actions.getText(heading, "Home Heading");
    }

    @Override
    public boolean isPageLoaded() {
        return actions.isDisplayed(heading, "Home Heading");
    }
}
