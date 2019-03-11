package com.borui.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/toukashiwaakira/Desktop/chromedriver";
    private final String USER_URL = "https://mail.google.com/mail/u/0/#inbox";
    private final String Valid_GMAIL = "barry.angela111@gmail.com";
    private final String Invalid_GMAIL = "barry.angela111";
    private final String VIEW_MESSAGE = "link_vsm";
    private final String RECIPIENT = "borui.tao@mail.mcgill.ca";
    private final String SUBJECT = "ecse428";

    private boolean first = true;
    // Given
    @Given("^I am a user with an existing account$")
    public void givenUserWithExistingAccount() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(USER_URL);

        WebElement emailElement = driver.findElement(By.id("identifierId"));

        //If the user has not logged in
        if (emailElement != null) {
            System.out.println("Please login...");
            emailElement.sendKeys(Valid_GMAIL);

            //click on "next"
            WebElement nextButtonEmail = driver.findElement(By.className("CwaK9"));
            nextButtonEmail.click();

            //wait for the password input box appears and enter the password
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
            WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
            System.out.println("Entering the password...");
            password.sendKeys("CS421g11");
            System.out.println("Password entered!");

            //click on the "next" button
            WebElement nextButtonPassword = driver.findElement(By.className("CwaK9"));
            nextButtonPassword.click();
        } else {
            //If the user has logged in
            System.out.println("Already logged in");
        }
    }

    @And("I am on the \"compose new message\" page with the recipient email and subject specified")
    public void andIamOnComposeNewMessagePage() throws Throwable {
        System.out.println("Attempting to find the compose button.. ");
        WebElement composeButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button'][@tabindex='0'][@gh='cm']")));

        System.out.println("compose button found! ");
        composeButton.click();

        //use TAB key from the keyboard to enter the recipient email
        System.out.println("Attempting to specify the recipient.. ");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        WebElement receiver = driver.findElement(By.xpath("//textarea[@rows='1'][@name='to'][@role='combobox']"));
        System.out.println("recipient found!");
        receiver.sendKeys(RECIPIENT);
        System.out.println("recipient sent!");

        //verify that the recipient email has been entered
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.textToBePresentInElementValue(receiver, RECIPIENT));
        System.out.println("recipient specified!");

        //enter the subject of the email
        System.out.println("Attempting to specify the subject.. ");
        WebElement subject = (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='subjectbox'][@placeholder='Subject']"))));
        System.out.println("Subject found!");
        subject.sendKeys(SUBJECT);
        System.out.println("Subject sent!");

        //verify that the subject of the email has been entered
        wait.until(ExpectedConditions.textToBePresentInElementValue(subject, SUBJECT));
        System.out.println("Subject Specified!\n");
    }

    @And("I am on the \"compose new message\" page with the recipient email and subject specified, but the recipient email is invalid")
    public void andIamOnComposeNewMessagePageInvalidEmail() throws Throwable {
        System.out.println("Attempting to find the compose button.. ");
        WebElement composeButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='button'][@tabindex='0'][@gh='cm']")));

        System.out.println("compose button found! ");
        composeButton.click();

        //use TAB key from the keyboard to enter the recipient email
        System.out.println("Attempting to specify the recipient.. ");
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        WebElement receiver = driver.findElement(By.xpath("//textarea[@rows='1'][@name='to'][@role='combobox']"));

        //enter an invalid email
        System.out.println("recipient found!");
        receiver.sendKeys(Invalid_GMAIL);
        System.out.println("recipient sent!");

        //verify that the recipient email has been entered
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.textToBePresentInElementValue(receiver, RECIPIENT));
        System.out.println("recipient specified!");

        System.out.println("Attempting to specify the subject.. ");
        WebElement subject = (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='subjectbox'][@placeholder='Subject']"))));
        System.out.println("Subject found!");

        //enter the subject of the email
        subject.sendKeys(SUBJECT);
        System.out.println("Subject sent!");

        //verify that the subject of the email has been entered
        wait.until(ExpectedConditions.textToBePresentInElementValue(subject, SUBJECT));
        System.out.println("Subject Specified!\n");
    }

    @And("^I have an image attachment in the email$")
    public void andIHaveImageAttachment() throws Throwable {
        pressAttachFileButtonAndSelectAnImage();
    }

    /*
     * To upload an image, first click on the "attach" button, then use the keys from the keyboard
     * to select a image and upload it using the ENTER key
     *
     * */
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

        //A pre-existing image is named with "img.png". This image can be selected by pressing the "I" key
        //on the keyboard
        robot.keyPress(KeyEvent.VK_I);
        robot.keyRelease(KeyEvent.VK_I);

        //Press the enter key to upload a file
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000);
        System.out.println("Image uploaded! \n");
    }

    @When("I press \"attach files\" and select a regular file and I press \"open\"")
    public void pressAttachFileButtonAndSelectARegularFile() throws Throwable {
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

        //A pre-existing regular file is named with "file.txt". This file can be selected by pressing the "F" key
        //on the keyboard
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_F);

        //Press the enter key to upload a file
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
        System.out.println("Attempting to find the send button...");

        //The send button can be selected by pressing the TAB key on the keyboard.
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        System.out.println("Clicking send button");
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    //Then

    // This is the method to confirm that the email was actually sent.
    @Then("^the email should be sent to the recipient with the image attachment$")
    public void emailBeSentWithImageAttachment() throws Throwable {
        System.out.println("Waiting for email confirmation appears...");

        //If the view message button appears, then the email has been sent
        WebElement viewMessage = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.id(VIEW_MESSAGE)));
        System.out.print("Email confirmation appears!\n");

        viewMessage.click();

        System.out.println("Clicking delete button.");
        Assert.assertTrue(driver.findElements(By.tagName("img")).size() > 0);
        assertInInitialState();
    }

    @Then("^the email should be sent to the recipient with both image attachments$")
    public void emailBeSentWithBothImageAttachments() throws Throwable {
        System.out.println("Waiting for email confirmation appears...");

        //If the view message button appears, then the email has been sent
        WebElement viewMessage = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.id(VIEW_MESSAGE)));
        System.out.print("Email confirmation appears!\n");

        viewMessage.click();

        System.out.println("Clicking delete button.");
        Assert.assertTrue(driver.findElements(By.tagName("img")).size() == 2);
        assertInInitialState();
    }

    @Then("^the email should be sent to the recipient without the image attachment$")
    public void emailBeSentWithoutImageAttachments() throws Throwable {
        System.out.println("Waiting for email confirmation appears...");

        WebElement viewMessage = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.id(VIEW_MESSAGE)));
        System.out.print("Email confirmation appears!\n");

        viewMessage.click();

        System.out.println("Clicking delete button.");
        // Product name should not be in active cart
        Assert.assertTrue(driver.findElements(By.tagName("img")).size() == 0);
        assertInInitialState();
    }

    @Then("^the email should be sent to the recipient with the regular file attachment$")
    public void emailBeSentWithRegularFileAttachments() throws Throwable {
        emailBeSentWithoutImageAttachments();
    }

    @Then("^an error dialog should be popped up indicating that the email address is invalid$")
    public void errorDialogPoppedUp() throws Throwable {
        Assert.assertTrue(driver.findElements(By.xpath("//button[@name='ok']")).size() > 0);
        returnToInitialState();
    }

    // Helper functions

    /* This is the method to ensure the system is returned to the initial state after tests are run
     * The initial state can be reached using the user_url
     */
    private void returnToInitialState() {
        goTo(USER_URL);
    }

    /* This is the method to check the system is in the appropriate initial state after tests are run
     * The system is in the initial state if the user can compose another email by clicking the compose button
     */
    private void assertInInitialState() {
        //check if the user can compose another message by validating the existence of the compose button
        Assert.assertTrue(driver.findElements(By.xpath("//div[@role='button'][@tabindex='0'][@gh='cm']")).size() > 0);
    }

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
