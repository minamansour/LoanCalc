package com.zopa.loan.app;

import java.util.List;
import java.util.Optional;

import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;

/**
 * <p>
 * Interface representing a service that produces an optional {@link Quote}
 * based on loan amount and a list of {@link Lender}.
 * </p>
 *
 * @author Mina Mansour
 */
public interface QuoteService {

	/**
	 * <p>
	 * Returns an optional {@link Quote} based on loan amount and a list of
	 * {@link Lender} instances.
	 * </p>
	 *
	 * <p>
	 * If it is not possible to calculate a {@link Quote} then an
	 * Optional.empty() is returned.
	 * </p>
	 *
	 * @param loanAmount
	 *            The amount of the loan.
	 * @param lenders
	 *            List of {@link Lender} instances.
	 * @return optional {@link Quote} instances.
	 */
	public Optional<Quote> calculateQuote(Integer loanAmount, List<Lender> lenders);
}
