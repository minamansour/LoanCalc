package com.zopa.loan.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;

/**
 * <p>
 * Interface representing a service that produces a list of {@link Contribution}
 * instances.
 * </p>
 *
 * <p>
 * In this interface is defined {@link #bestRate}, that implements the criteria
 * of best possible rate required for the technical tests. But can define other
 * kinds of criteria.
 * </p>
 *
 * @author Mina Mansour
 */
public interface ContributionCalculator {

	/**
	 * <p>
	 * Calculates an optional list of {@link Contribution} based on loan amount
	 * and a list of {@link Lender}.
	 * </p>
	 *
	 * @param loanAmount
	 *            The amount of the loan.
	 * @param lenders
	 *            List of {@link Lender} instances.
	 * @return an optional list of {@link Contribution} instances.
	 */
	Optional<List<Contribution>> calculate(Integer loanAmount, List<Lender> lenders);

	ContributionCalculator bestRate = (Integer loanAmount, List<Lender> lenders) -> {

		if (loanAmount <= 100) {
			throw new IllegalArgumentException("Parameter loanAmount should be 100 or greater.");
		}
		if (lenders.isEmpty()) {
			throw new IllegalArgumentException("Parameter lenders should be a non empty list.");
		}

		List<Contribution> contributions = new ArrayList<>();

		lenders.sort(Comparator.comparing(Lender::getRate));

		int accAmount = 0;
		Iterator<Lender> lendersIterator = lenders.iterator();
		while (accAmount < loanAmount && lendersIterator.hasNext()) {

			Lender lender = lendersIterator.next();

			Integer lenderAvailable = lender.getAvailable();

			if (lenderAvailable >= 0) {
				int lenderContributedAmount = (accAmount + lenderAvailable <= loanAmount) ? lenderAvailable
						: loanAmount - accAmount;

				Contribution contribution = new Contribution(lender, lenderContributedAmount);

				contributions.add(contribution);

				accAmount += lenderContributedAmount;
			}
		}

		if (accAmount == loanAmount) {
			return Optional.of(contributions);

		} else {
			return Optional.empty();
		}
	};
	
}
