/**
 * 
 */
package com.zopa.loan.app;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zopa.loan.model.Lender;

/**
 * @author Mina Mansour
 */
public class QuoteCalculatorTest {

	QuoteCalculator quoteCalculator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		quoteCalculator = new QuoteCalculator();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		quoteCalculator = null;
	}

	@Test
	public void getLendersTest() throws IOException {
		List<Lender> lenders = quoteCalculator.getLenders("./src/test/resource/Market_Data.csv");
		int excepectedSize = 7;
		Assert.assertNotNull(lenders);
		Assert.assertEquals(excepectedSize, lenders.size());
	}

	@Test(expected = NumberFormatException.class)
	public void getLendersBadFormatedTest() throws IOException {
		quoteCalculator.getLenders("./src/test/resource/Market_Data_bad_formated.csv");
	}

	@Test(expected = IOException.class)
	public void getLendersFileNotFoundTest() throws IOException {
		quoteCalculator.getLenders("./src/test/resource/Market_Data_1.csv");
	}

	@Test
	public void getLoanAmountTest() {
		Assert.assertEquals(quoteCalculator.getLoanAmount("1000"), new Integer(1000));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLoanAmountWrongRangeTest() {
		quoteCalculator.getLoanAmount("150000");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLoanAmountWrongAmountTest() {
		quoteCalculator.getLoanAmount("50");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLoanAmountNullAmountTest() {
		quoteCalculator.getLoanAmount(null);
	}
}
