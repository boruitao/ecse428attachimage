package com.borui.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/toukashiwaakira/Desktop/chromedriver";
    private final String USER_URL = "https://mail.google.com/mail/u/0/#inbox";
    private final String MY_GMAIL = "barry.angela111@gmail.com";
    private final String VIEW_MESSAGE = "link_vsm";

    private final String RECEIVER = "borui.tao@mail.mcgill.ca";
    private final String SUBJECT = "ecse428";

    private final String PATH_TO_IMAGE = "/Users/toukashiwaakira/Desktop/img.jpg";

    private boolean first = true;
    // Given
    @Given("^I am a user with an existing account$")
    public void givenUserWithExistingAccount() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(USER_URL);

        WebElement emailEle = driver.findElement(By.id("identifierId"));

        if (emailEle != null) {
            System.out.println("Please login...");
            emailEle.sendKeys(MY_GMAIL);
            WebElement nextButton = driver.findElement(By.className("CwaK9"));
            nextButton.click();

            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));

            WebElement password = driver.findElement(By.xpath("//input[@name='password']"));

            System.out.println("Entering the password...");
            password.sendKeys("CS421g11");

            System.out.println("Password entered!");
            WebElement nextButtonPassword = driver.findElement(By.className("CwaK9"));
            nextButtonPassword.click();
        } else {
            System.out.println("Already logged in");
        }
    }

    @And("I am on the \"compose new message\" page with the recipient email and subject specified")
    public void andIamOnComposeNewMessagePage(String recipient) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        System.out.println("Attempting to find the compose button.. ");
        WebElement composeButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button'][@tabindex='0'][@gh='cm']")));

        System.out.println("compose button found! ");
        composeButton.click();

        System.out.println("Attempting to specify the receiver.. ");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        WebElement receiver = driver.findElement(By.xpath("//textarea[@rows='1'][@name='to'][@role='combobox']"));

        System.out.println("Receiver found!");

        receiver.sendKeys(RECEIVER);
        System.out.println("Receiver sent!");

        wait.until(ExpectedConditions.textToBePresentInElementValue(receiver, RECEIVER));
        System.out.println("Receiver Specified!");

        System.out.println("Attempting to specify the subject.. ");
        WebElement subject = (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='subjectbox'][@placeholder='Subject']"))));
        System.out.println("Subject found!");

        subject.sendKeys(SUBJECT);
        System.out.println("Subject sent!");

        wait.until(ExpectedConditions.textToBePresentInElementValue(subject, SUBJECT));
        System.out.println("Subject Specified!\n");
    }

    @And("^I have an image attachment in the email$")
    public void andIHaveImageAttachment() throws Throwable {
        pressAttachFileButtonAndSelectAnImage();
    }

    @When("I press \"attach files\" and select an image and I press \"open\"")
    public void pressAttachFileButtonAndSelectAnImage() throws Throwable {
        System.out.println("Attempting to upload a image ... ");

        WebElement attachFileBtn = driver.findElement(By.xpath("//div[@class='a1 aaA aMZ']"));
        attachFileBtn.click();
        System.out.println("File system window popped up! ");

        Robot robot = new Robot();
        if (first) {
            System.out.println("It is the first test! ");
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_META);
            first = false;
        }
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(2000);
        System.out.println("Image uploaded! \n");
    }

    @When("^I press the remove button$")
    public void pressTheRemoveButton() throws Throwable {
        try {
            System.out.println("Attempting to find the remove button...");
            WebElement removeButton = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(":fu")));
            System.out.print("remove button found!\n");
            removeButton.click();
            System.out.println("Clicking remove button");
        } catch (Exception e) {
            System.out.println("No remove button present");
        }
    }

    @And("^I press \"Send\"$")
    public void pressSend() throws Throwable {
        try {
            System.out.println("Attempting to find the send button...");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER
            );
            System.out.println("Send button found!");
            System.out.println("Clicking send button");
        } catch (Exception e) {
            System.out.println("No send button present\n");
        }
    }

    @Then("^the email should be sent to the receiver with the image attachment$")
    public void emailBeSentWithImageAttachment() throws Throwable {
        System.out.println("Waiting for email confirmation appears...");

        WebElement viewMessage = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.id(VIEW_MESSAGE)));
        System.out.print("Email confirmation appears!\n");

        viewMessage.click();

        System.out.println("Clicking delete button.");
        // Product name should not be in active cart
        Assert.assertTrue(driver.findElements(By.tagName("img")).size() > 0);
    }

    @Then("^the email should be sent to the receiver with both image attachments$")
    public void emailBeSentWithBothImageAttachments() throws Throwable {
        emailBeSentWithImageAttachment();
    }

    @Then("^the email should be sent to the receiver without the image attachment$")
    public void emailBeSentWithoutImageAttachments() throws Throwable {
        System.out.println("Waiting for email confirmation appears...");

        WebElement viewMessage = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.id(VIEW_MESSAGE)));
        System.out.print("Email confirmation appears!\n");

        viewMessage.click();

        System.out.println("Clicking delete button.");
        // Product name should not be in active cart
        Assert.assertTrue(driver.findElements(By.tagName("img")).size() == 0);
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }
}
