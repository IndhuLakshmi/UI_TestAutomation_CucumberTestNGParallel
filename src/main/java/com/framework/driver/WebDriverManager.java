package com.framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver management using ThreadLocal.
 * Single Responsibility: manage lifecycle of WebDriver instances per thread.
 */
public class WebDriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver d = driver.get();
        if (d != null) {
            d.quit();
            driver.remove();
        }
    }
}
