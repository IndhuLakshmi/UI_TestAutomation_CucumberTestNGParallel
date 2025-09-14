package com.framework.hooks;

import com.framework.config.ConfigReader;
import com.framework.driver.WebDriverFactory;
import com.framework.driver.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.*;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Cucumber Hooks for setup/teardown and per-step screenshots.
 */
public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        String browser = System.getProperty("browser", ConfigReader.get("browser"));
        WebDriver driver = WebDriverFactory.createDriver(browser);
        WebDriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));
        logger.info("Started scenario: {}", scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            WebDriver driver = WebDriverManager.getDriver();
            if (driver == null) return;
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot-step");
        } catch (Exception e) {
            logger.warn("Failed to take step screenshot: {}", e.getMessage());
        }
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.error("Scenario failed: {}", scenario.getName());
            } else {
                logger.info("Scenario passed: {}", scenario.getName());
            }
        } finally {
            WebDriverManager.quitDriver();
            logger.info("Closed driver for scenario: {}", scenario.getName());
        }
    }
}
