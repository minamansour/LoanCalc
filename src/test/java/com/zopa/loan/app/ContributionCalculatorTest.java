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

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;

/**
 * @author Mina Mansour
 */
public class ContributionCalculatorTest {

	ContributionCalculator contributionCalculator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		contributionCalculator = ContributionCalculator.bestRate;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		contributionCalculator = null;
	}

	@Test
	public void calculateTest() {
		Integer loanAmount = 1000;

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 5000);
		lenders.add(lender);

		Optional<List<Contribution>> calculate = contributionCalculator.calculate(loanAmount, lenders);

		Assert.assertNotNull(calculate.get());

	}

	
	@Test
	public void calculateBestRateTest() {
		Integer loanAmount = 1000;

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 50);
		lenders.add(lender);

		lender = new Lender("Dale", BigDecimal.valueOf(0.051), 400);
		lenders.add(lender);

		lender = new Lender("Mary", BigDecimal.valueOf(0.04), 600);
		lenders.add(lender);


		Optional<List<Contribution>> calculate = contributionCalculator.calculate(loanAmount, lenders);

		Assert.assertNotNull(calculate.get());
		Assert.assertEquals("Mary",calculate.get().get(0).getLender().getName());

	}
	
	@Test
	public void calculateNoAvailableContributionTest() {
		Integer loanAmount = 1000;

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 500);
		lenders.add(lender);

		Optional<List<Contribution>> calculate = contributionCalculator.calculate(loanAmount, lenders);

		Assert.assertFalse(calculate.isPresent());

	}

	@Test(expected = IllegalArgumentException.class)
	public void calculateWrongLoanAmountTest() {
		Integer loanAmount = 90;

		List<Lender> lenders = new ArrayList<Lender>();
		Lender lender = new Lender("Jane", BigDecimal.valueOf(0.05), 5000);
		lenders.add(lender);

		contributionCalculator.calculate(loanAmount, lenders);

	}

}
