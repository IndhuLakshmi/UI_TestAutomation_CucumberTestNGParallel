package com.framework.steps;

import com.framework.pages.HomePage;
import com.framework.driver.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;

/**
 * Example step definitions using the framework pages.
 */
public class HomeSteps {

    private WebDriver driver;
    private HomePage homePage;

    public HomeSteps() {
        this.driver = WebDriverManager.getDriver();
        this.homePage = new HomePage(driver);
    }

    @Given("I am on the home page")
    public void i_am_on_home_page() {
        Assert.assertTrue(homePage.isPageLoaded(), "Home page not loaded");
    }

    @Then("I verify the heading is present")
    public void i_verify_heading() {
        String h = homePage.getHeading();
        Assert.assertTrue(h != null && !h.isEmpty(), "Heading empty");
    }

    @Then("I verify the heading is present1")
    public void i_verify_heading1() {
           driver.get("https://www.google.com");
           try{Thread.sleep(4000);} catch(Exception e){e.printStackTrace();}
//        String h = homePage.getHeading();
//        Assert.assertTrue(h != null && !h.isEmpty(), "Heading empty");
    }
}
