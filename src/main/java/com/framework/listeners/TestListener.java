package com.framework.listeners;

import com.framework.driver.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * TestNG listener for logging and screenshots on failure.
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting Test: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getName());
        try {
            byte[] bytes = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            String path = "target/screenshots/" + result.getName() + ".png";
            File dest = Paths.get(path).toFile();
            dest.getParentFile().mkdirs();
            FileUtils.writeByteArrayToFile(dest, bytes);
            logger.info("Saved screenshot to {}", path);
        } catch (Exception e) {
            logger.warn("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("All tests finished for: {}", context.getName());
    }
}
