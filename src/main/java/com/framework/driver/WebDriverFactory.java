package com.framework.driver;

import com.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;

/**
 * Factory Design Pattern: Responsible for creating WebDriver instances.
 * Supports external driver executables via config properties and falls back to WebDriverManager.
 * Open/Closed: extend by adding new cases for browsers.
 */
public class WebDriverFactory {

    private static String resolveCustomDriverPath(String key) {
        String path = ConfigReader.get(key);
        if (path != null && !path.trim().isEmpty()) {
            File f = new File(path);
            if (f.exists() && f.isFile()) {
                return f.getAbsolutePath();
            } else {
                throw new IllegalArgumentException("Driver path configured but not found: " + path);
            }
        }
        return null;
    }

    public static WebDriver createDriver(String browser) {
        browser = browser == null ? "chrome" : browser.toLowerCase();
        switch (browser) {
            case "chrome": {
                String custom = resolveCustomDriverPath("chromeDriverPath");
                if (custom != null) {
                    System.setProperty("webdriver.chrome.driver", custom);
                } else {
                    WebDriverManager.chromedriver().setup();
                }
                ChromeOptions options = new ChromeOptions();
                // Add any default options here
                return new org.openqa.selenium.chrome.ChromeDriver(options);
            }
            case "firefox": {
                String custom = resolveCustomDriverPath("geckoDriverPath");
                if (custom != null) {
                    System.setProperty("webdriver.gecko.driver", custom);
                } else {
                    WebDriverManager.firefoxdriver().setup();
                }
                FirefoxOptions options = new FirefoxOptions();
                return new org.openqa.selenium.firefox.FirefoxDriver(options);
            }
            case "edge": {
                String custom = resolveCustomDriverPath("edgeDriverPath");
                if (custom != null) {
                    System.setProperty("webdriver.edge.driver", custom);
                } else {
                    WebDriverManager.edgedriver().setup();
                }
                EdgeOptions options = new EdgeOptions();
                return new org.openqa.selenium.edge.EdgeDriver(options);
            }
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
