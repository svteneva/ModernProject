package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.modern.EmailSignUpTest;
import test.modern.ProductPriceCalculationTest;
import test.modern.SortOrderTest;

@RunWith(Suite.class)
@SuiteClasses({ EmailSignUpTest.class,
				ProductPriceCalculationTest.class,
				SortOrderTest.class })

/**
 * Runs all tests at once
 * @author Svetlana Teneva
 *
 */
public class AllModernTests {
}