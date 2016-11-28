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
 * Product Catalog Sort Order tests
 * @author Svetlana Teneva
 *
 */
public class SortOrderTest {
	private WebDriver driver = new FirefoxDriver();

	private static final String URL_SOFA = "http://www.modern.co.uk/c/Modern_Sofa.htm";
	private static final String FIRST_ELEMENT_XPATH = "//*[@id='mod_block_products_list']/div[2]/div[1]/div[2]/a";
	private static final String FIRST_ELEMENT_AVAILABILITY = "Jean Fabric Chair Bed";
	private static final String FIRST_ELEMENT_ALPHABETICAL = "Abbie 3 Seater Sofa Dark...";
	private static final String LAST_ELEMENT_ALPHABETICAL = "Winford 3 Seater Sofa...";
	private static final String ORDER_DROPDOWN = "sorts";
	
	@Before
	public void setUp() throws Exception {	
		driver.navigate().to(URL_SOFA);
	}
	
	@After
	public void tearDown() throws Exception {
	    driver.quit();
	}
	
	/*
	 * Alphabetical sort
	 * Positive test
	 */
	@Test
	public void testSortOrderAlphabetical() throws InterruptedException {
		assertEquals(FIRST_ELEMENT_AVAILABILITY, getFirstElementText());
		selectSortOption(4);
		getWebDriverWait().until(ExpectedConditions.textToBe(getFirstElementLocation(), FIRST_ELEMENT_ALPHABETICAL));
	}
	
	/*
	 * Alphabetical sort
	 * Negative test
	 */
	@Test
	public void testSortOrderAlphabeticalNegative() throws InterruptedException {
		assertEquals(FIRST_ELEMENT_AVAILABILITY, getFirstElementText());
		selectSortOption(4);
		getWebDriverWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(getFirstElementLocation(), LAST_ELEMENT_ALPHABETICAL)));
	}
	
	/**
	 * Selects sorting order
	 * @param filter  number between 1 and 7
	 */
	private void selectSortOption(int filter) {
		Select sort = new Select(driver.findElement(By.className(ORDER_DROPDOWN)));
		String option;
		switch(filter) {
			case 1: option = "Price: Low to High";
					sort.selectByVisibleText(option);
					break;
			case 2: option = "Price: High to Low";
					sort.selectByVisibleText(option);
					break;
			case 3: option = "Top Selling";
					sort.selectByVisibleText(option);
					break;
			case 4: option = "Alphabetical: A to Z";
					sort.selectByVisibleText(option);
					break;
			case 5: option = "Popularity";
					sort.selectByVisibleText(option);
					break;
			case 6: option = "Availability";
					sort.selectByVisibleText(option);
					break;
			case 7: option = "Best Match";
					sort.selectByVisibleText(option);
					break;
			default: option = "Availability";
					sort.selectByVisibleText(option);
					break;
		}
	}
	
	/**
	 * Gets the locator of the first product on the page
	 * @return the locator of the first product on the page
	 */
	private By getFirstElementLocation() {
		return By.xpath(FIRST_ELEMENT_XPATH);
	}
	
	/**
	 * Gets the text of the first product on the page
	 * @return  the text of the first product on the page
	 */
	private String getFirstElementText() {
		return driver.findElement(getFirstElementLocation()).getText();
	}
	
	/**
	 * Prepare WebDriverWait instance
	 * @return
	 */
	private WebDriverWait getWebDriverWait() {
		return new WebDriverWait(driver, 10);
	}
}
