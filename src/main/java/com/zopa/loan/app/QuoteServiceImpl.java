package com.zopa.loan.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;

/**
 * <p>
 * Implementing the {@link QuoteService} interface for loans of fixed 36 months.
 * </p>
 *
 * <p>
 * Requires an instance of {@link ContributionCalculator}, so it can calculate a
 * list of {@link Contribution}.
 * </p>
 *
 * @author Mina Mansour
 */
public class QuoteServiceImpl implements QuoteService {

	private static final int NUMBER_OF_MONTHS = 36;
	private static final int MONTHS_PER_YEAR = 12;

	private ContributionCalculator contributionCalculator;

	public QuoteServiceImpl(ContributionCalculator contributionCalculator) {
		this.contributionCalculator = contributionCalculator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Quote> calculateQuote(Integer loanAmount, List<Lender> lenders) {

		Optional<List<Contribution>> optContributions = contributionCalculator.calculate(loanAmount, lenders);

		return optContributions.map(this::calculateQuote);
	}

	/**
	 * <p>
	 * Returns a {@link Quote} based on a list of {@link Contribution}
	 * instances.
	 * </p>
	 *
	 * @param contributions
	 *            List of {@link Contribution} instances.
	 * @return a {@link Quote}
	 */
	private Quote calculateQuote(List<Contribution> contributions) {

		//Calculate rate 
		BigDecimal quoteRate = calculateRate(contributions);

		quoteRate = quoteRate.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP);

		// Calculate Monthly Repayments
		BigDecimal quoteMonthlyRepayment = contributions.stream().map(this::calculateMonthlyRepayment)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		quoteMonthlyRepayment = quoteMonthlyRepayment.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		// Calculate total Repayments
		BigDecimal quoteTotalRepayment = quoteMonthlyRepayment.multiply(BigDecimal.valueOf(NUMBER_OF_MONTHS))
				.setScale(2, BigDecimal.ROUND_HALF_UP);

		return new Quote(quoteRate, quoteMonthlyRepayment, quoteTotalRepayment);
	}

	/**
	 * <p>
	 * Returns the combined rate of a list of {@link Contribution} instances.
	 * </p>
	 *
	 * @param contributions
	 *            List of contributions
	 * @return combined rate
	 */
	private BigDecimal calculateRate(List<Contribution> contributions) {
		
		//Summation for all the available contributions amount.  
		int totalAmount = contributions.stream().mapToInt(Contribution::getAmount).sum();
		
		BigDecimal rate = contributions.stream().map((Contribution c) -> calculateCombinedRate(totalAmount, c))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return rate;
	}

	/**
	 * <p>
	 * Returns the contribution of a {@link Contribution} for the global rate of
	 * the loan.
	 * </p>
	 *
	 * @param loanAmount
	 *            The total amount of the loan
	 * @param contribution
	 *            The contribution
	 * @return the contribution to the global rate of the loan.
	 */
	private BigDecimal calculateCombinedRate(Integer loanAmount, Contribution contribution) {
		BigDecimal combinedRate = BigDecimal.valueOf(contribution.getAmount())
				.divide(BigDecimal.valueOf(loanAmount), 10, BigDecimal.ROUND_HALF_UP)
				.multiply(contribution.getLender().getRate());
		return combinedRate;
	}

	/**
	 * <p>
	 * Returns the monthly repayments for a {@link Contribution}.
	 * </p>
	 *
	 * @param contribution
	 *            a {@link Contribution}
	 * @return the monthly repayments
	 */
	private BigDecimal calculateMonthlyRepayment(Contribution contribution) {

		BigDecimal rate = contribution.getLender().getRate();

		Integer amount = contribution.getAmount();

		BigDecimal monthlyRate = BigDecimal
				.valueOf(Math.pow(rate.add(BigDecimal.ONE).doubleValue(), 1 / (double) MONTHS_PER_YEAR))
				.subtract(BigDecimal.ONE);

		BigDecimal monthlyRepayment = monthlyRate.multiply(BigDecimal.valueOf(amount))
				.divide(BigDecimal.ONE.subtract(
						BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(monthlyRate).doubleValue(), -NUMBER_OF_MONTHS))),
						10, BigDecimal.ROUND_HALF_UP);

		return monthlyRepayment;
	}
}
