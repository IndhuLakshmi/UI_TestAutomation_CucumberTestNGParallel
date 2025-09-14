package com.framework.utils;

import org.openqa.selenium.WebElement;

/**
 * Interface Segregation Principle - define compact, focused actions for UI interactions.
 */
public interface ISeleniumActions {
    void click(WebElement element, String elementName);
    void type(WebElement element, String text, String elementName);
    String getText(WebElement element, String elementName);
    boolean isDisplayed(WebElement element, String elementName);
}
