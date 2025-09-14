# UI Automation Framework (Java + Selenium + Cucumber + TestNG)

This project is a production-oriented UI automation framework scaffold that implements:
- Factory pattern for WebDriver with support for external driver executables and automatic driver management.
- ThreadLocal WebDriver management for thread safety during parallel execution.
- Page Object Model (POM) with PageFactory.
- SOLID principles applied across components.
- Robust wait utilities (explicit and fluent waits), RetryAnalyzer and TestNG listener.
- Cucumber + TestNG integration with hooks that attach screenshots for each step.
- Externalized configuration and logging (Log4j2).
- Data utilities: ExcelReader, FakerUtil, JSON reader.

## How to run
1. Edit `src/main/resources/config.properties` (browser, baseUrl, timeouts, driver paths if needed).
2. Run `mvn clean test` (requires JDK 17+).

## Notes
- Cucumber version used: 7.28.2 (stable on Maven Central).
- Selenium: 4.24.0.
- WebDriverManager is included to download drivers if external paths are not provided.
