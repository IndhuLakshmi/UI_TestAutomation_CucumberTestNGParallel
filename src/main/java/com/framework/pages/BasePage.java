package com.framework.pages;

import com.framework.utils.ISeleniumActions;
import com.framework.utils.SeleniumActionsImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage - common functionality for all pages.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected ISeleniumActions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new SeleniumActionsImpl();
        PageFactory.initElements(driver, this);
    }

    public abstract boolean isPageLoaded();
}
