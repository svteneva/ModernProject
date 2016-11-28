package test.modern;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Product Price Calculation tests
 * @author Svetlana Teneva
 *
 */
public class ProductPriceCalculationTest {
	private WebDriver driver = new FirefoxDriver();

	private static final String URL_SOFA = "http://www.modern.co.uk/c/Modern_Sofa.htm";
	private static final String FIRST_ELEMENT_XPATH = "//*[@id='mod_block_products_list']/div[2]/div[1]/div[2]/a";
	private static final String ADD_TO_BASKET_BUTTON = "ws-btnaddcart";
	private static final String TOTAL_BASKET_PRICE_XPATH = "//span[@class='totalBasketPrice']";
	private static final String UNIT_PRICE_XPATH = "//div[@id='cartID0']/div[2]/div[2]/div[1]";
	private static final String CHECKOUT_BUTTON_LINK = "//a[@class='check-out-button']";
	private static final String QUANTITY_DROPDOWN_XPATH = "//select[@name='quantity']";
	private static final String EMPTY_SHOPPING_CART_MESSAGE_ELEMENT = "//div[@class='no-items']";
	
	@Before
	public void setUp() throws Exception {	
		driver.get(URL_SOFA);
	}
	
	@After
	public void tearDown() throws Exception {
	    driver.quit();
	}
	
	/*
	 * Product Price Calculation
	 * Positive test
	 */
	@Test
	public void testPricePerQuantity() {
		// Test with quantity 5
		int testQuantity = 5;
		
		makePurchase(testQuantity);

		// Wait for basket pop up
		getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(CHECKOUT_BUTTON_LINK)));

		double unitPrice = parsePoundPrice(driver.findElement(By.xpath(UNIT_PRICE_XPATH)).getText());
		double totalPrice = parsePoundPrice(driver.findElement(By.xpath(TOTAL_BASKET_PRICE_XPATH)).getText());

		assertEquals(totalPrice, unitPrice * testQuantity, 0.001);
	}
	
	/*
	 * Product Price Calculation
	 * Negative test
	 */
	@Test
	public void testZeroQuantity() {
		// Test with quantity 0
		makePurchase(0);
		getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EMPTY_SHOPPING_CART_MESSAGE_ELEMENT)));
	}
	
	/**
	 * Makes purchase of an item
	 * @param quantity Quantity to be selected on purchase
	 */
	private void makePurchase(int quantity) {
		// Click on first product
		driver.findElement(By.xpath(FIRST_ELEMENT_XPATH)).click();
		
		// Wait for page to load
		getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.id(ADD_TO_BASKET_BUTTON)));

		// Select the given quantity
		Select quantityDropdown = new Select(driver.findElement(By.xpath(QUANTITY_DROPDOWN_XPATH)));
		quantityDropdown.selectByValue(String.valueOf(quantity));

		// Add product to the basket
		driver.findElement(By.id(ADD_TO_BASKET_BUTTON)).click();
	}
	
	/**
	 * Prepare WebDriverWait instance
	 * @return
	 */
	private WebDriverWait getWebDriverWait() {
		return new WebDriverWait(driver, 20, 5000);
	}

	/**
	 * Parses pound price and returns the quantity
	 * @param priceAsString Price with currency symbol
	 * @return Price value only
	 */
	private double parsePoundPrice(String priceAsString) {
		return Double.valueOf(priceAsString.split("\u00a3")[1]);
	}
}
