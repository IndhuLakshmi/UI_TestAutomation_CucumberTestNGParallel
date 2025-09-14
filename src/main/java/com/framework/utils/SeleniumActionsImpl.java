package com.framework.utils;

import com.framework.driver.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

/**
 * Robust implementation for Selenium interactions with explicit and fluent waits.
 * Dependency Inversion: code depends on the ISeleniumActions interface.
 */
public class SeleniumActionsImpl implements ISeleniumActions {
    private static final Logger logger = LogManager.getLogger(SeleniumActionsImpl.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Duration polling = Duration.ofMillis(300);

    public SeleniumActionsImpl() {
        this.driver = WebDriverManager.getDriver();
        int explicitWait = 15;
        try {
            explicitWait = Integer.parseInt(System.getProperty("explicitWait",
                    System.getenv().getOrDefault("EXPLICIT_WAIT", "15")));
        } catch (Exception ignored) {}
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    @Override
    public void click(WebElement element, String elementName) {
        try {
            waitUntilClickable(element);
            element.click();
            logger.info("Clicked on {}", elementName);
        } catch (Exception e) {
            logger.error("Unable to click on {} - {}", elementName, e.getMessage());
            throw new RuntimeException("Click failed on " + elementName, e);
        }
    }

    @Override
    public void type(WebElement element, String text, String elementName) {
        try {
            waitUntilVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed '{}' into {}", text, elementName);
        } catch (Exception e) {
            logger.error("Unable to type into {} - {}", elementName, e.getMessage());
            throw new RuntimeException("Type failed on " + elementName, e);
        }
    }

    @Override
    public String getText(WebElement element, String elementName) {
        try {
            waitUntilVisible(element);
            String text = element.getText();
            logger.info("Text of {} is '{}'", elementName, text);
            return text;
        } catch (Exception e) {
            logger.error("Unable to getText from {} - {}", elementName, e.getMessage());
            throw new RuntimeException("getText failed on " + elementName, e);
        }
    }

    @Override
    public boolean isDisplayed(WebElement element, String elementName) {
        try {
            waitUntilVisible(element);
            boolean val = element.isDisplayed();
            logger.info("{} displayed? {}", elementName, val);
            return val;
        } catch (Exception e) {
            logger.warn("{} not displayed - {}", elementName, e.getMessage());
            return false;
        }
    }

    private void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void waitUntilClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement fluentFind(final By locator, int timeoutSeconds) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(polling)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver d) {
                return d.findElement(locator);
            }
        });
    }

    public byte[] takeScreenshotBytes() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            logger.error("Screenshot failed: {}", e.getMessage());
            return new byte[0];
        }
    }
}
