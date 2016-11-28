package test.modern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Email Sign-Up tests   
 * @author Svetlana Teneva
 *
 */
public class EmailSignUpTest {
	private WebDriver driver = new FirefoxDriver();

	private static final String URL = "http://www.modern.co.uk/";
	private static final String SUBSCRIBE_BUTTON = "#newsletter-signup>button";
	private static final String EMPTY_EMAIL_MESSAGE = "Enter an email address";
	private static final String ERROR_MESSAGE_ELEMENT_NAME = "newsletter-signup-errormsg";
	private static final String SUCCESS_MESSAGE_ELEMENT_NAME = "newsletter-signup-successmsg";
	private static final String EMAIL_FIELD = "newsletter-email";
	private static final String INCORRECT_EMAIL = ".test@test.com";
	private static final String CORRECT_EMAIL_TEMPLATE = "test#@mail.com";
	private static final String INCORRECT_EMAIL_MESSAGE = "Invalid email address";
	private static final String CORRECT_EMAIL_MESSAGE = "Email address subscribed";

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	/*
	 * Empty email field
	 * Negative test for Empty field 
	 */
	@Test
	public void testEmptyEmail() {
		// Presses Subscribe button
		driver.findElement(By.cssSelector(SUBSCRIBE_BUTTON)).click();
		// Check for empty field message
		getWebDriverWait().until(ExpectedConditions.textToBe(By.className(ERROR_MESSAGE_ELEMENT_NAME), EMPTY_EMAIL_MESSAGE));
	}

	/*
	 * Incorrect email address
	 * Negative test for Incorrect email format
	 */
	@Test
	public void testIncorrectEmail() {
		enterEmailAddress(INCORRECT_EMAIL);
		// Check for incorrect field message
		getWebDriverWait().until(ExpectedConditions.textToBe(By.className(ERROR_MESSAGE_ELEMENT_NAME), INCORRECT_EMAIL_MESSAGE));
	}

	/*
	 * Correct email address
	 * Positive test for Incorrect email format and Empty field 
	 */
	@Test
	public void testCorrectEmail() {
		// Generate unique e-mail
		String generatedEMail = CORRECT_EMAIL_TEMPLATE.replace("#", String.valueOf(System.currentTimeMillis()));
		enterEmailAddress(generatedEMail);
		// Check for correct field message
		getWebDriverWait().until(ExpectedConditions.textToBe(By.className(SUCCESS_MESSAGE_ELEMENT_NAME), CORRECT_EMAIL_MESSAGE));
	}

	/**
	 * Fills in Email Signup form and presses Subscribe button
	 * @param email email address
	 */
	private void enterEmailAddress(String email) {
		// Populates email address field
		driver.findElement(By.id(EMAIL_FIELD)).sendKeys(email);
		// Presses Subscribe button
		driver.findElement(By.cssSelector(SUBSCRIBE_BUTTON)).click();
	}
	
	/**
	 * Prepare WebDriverWait instance
	 * @return
	 */
	private WebDriverWait getWebDriverWait() {
		return new WebDriverWait(driver, 10);
	}
}
