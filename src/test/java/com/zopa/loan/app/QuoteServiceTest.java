/**
 * 
 */
package com.zopa.loan.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;

/**
 * @auther Mina Mansour
 */
public class QuoteServiceTest {

	QuoteService quoteService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		quoteService = new QuoteServiceImpl(ContributionCalculator.bestRate);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		quoteService = null;
	}

	@Test
	public void calculateQuoteTest() {
		Integer loanAmount = 1000;
		BigDecimal excepectedRate = BigDecimal.valueOf(5.0);
		BigDecimal excepectedMonthlyRepayment = BigDecimal.valueOf(29.92);
		BigDecimal excepectedTotalRepayment = BigDecimal.valueOf(1077.12);

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 5000);
		lenders.add(lender);

		Optional<Quote> calculateQuote = quoteService.calculateQuote(loanAmount, lenders);

		Assert.assertNotNull(calculateQuote.get());
		Assert.assertEquals(excepectedRate, calculateQuote.get().getRate());
		Assert.assertEquals(excepectedMonthlyRepayment, calculateQuote.get().getMonthlyRepayment());
		Assert.assertEquals(excepectedTotalRepayment, calculateQuote.get().getTotalRepayment());

	}

	@Test
	public void calculateQuoteBestRateTest() {
		Integer loanAmount = 1000;
		BigDecimal excepectedRate = BigDecimal.valueOf(4.4);
		BigDecimal excepectedMonthlyRepayment = BigDecimal.valueOf(29.68);
		BigDecimal excepectedTotalRepayment = BigDecimal.valueOf(1068.48);

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 50);
		lenders.add(lender);

		lender = new Lender("Dale", BigDecimal.valueOf(0.051), 400);
		lenders.add(lender);

		lender = new Lender("Mary", BigDecimal.valueOf(0.04), 600);
		lenders.add(lender);

		Optional<Quote> calculateQuote = quoteService.calculateQuote(loanAmount, lenders);

		Assert.assertEquals("Mary", lenders.get(0).getName());
		Assert.assertEquals(excepectedRate, calculateQuote.get().getRate());
		Assert.assertEquals(excepectedMonthlyRepayment, calculateQuote.get().getMonthlyRepayment());
		Assert.assertEquals(excepectedTotalRepayment, calculateQuote.get().getTotalRepayment());
	}

	@Test
	public void calculateQuoteNoAvailableOfferTest() {
		Integer loanAmount = 1000;

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 50);
		lenders.add(lender);

		Optional<Quote> calculateQuote = quoteService.calculateQuote(loanAmount, lenders);

		Assert.assertFalse(calculateQuote.isPresent());

	}

}
