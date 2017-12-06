package com.zopa.loan.app;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;
import com.zopa.loan.util.Logger;

/**
 *
 * @author Mina Mansour
 */
public class QuoteCalculator extends QuoteCalculateTemplate {

	public Optional<Quote> getQuote(List<Lender> lenders, Integer loanAmount) {

		QuoteService quoteService = new QuoteServiceImpl(ContributionCalculator.bestRate);

		Optional<Quote> optQuote = quoteService.calculateQuote(loanAmount, lenders);

		return optQuote;
	}

	public Integer getLoanAmount(String loanAmountParam) throws IllegalArgumentException {

		Integer loanAmount = parseLoanAmount(loanAmountParam);

		if (null == loanAmount || loanAmount < 1000 || loanAmount > 15000 || ((loanAmount % 100) != 0)) {
			throw new IllegalArgumentException("Loan amount should be between 1000 and 15000 (100 increments).");
		}
		return loanAmount;
	}

	public List<Lender> getLenders(String cvsFilePath) throws NumberFormatException, IOException {
		List<Lender> lenders = new CSVFileReader().processFile(cvsFilePath);
		return lenders;
	}

	public void displayQuote(Integer loanAmount, Optional<Quote> optQuote) {
		if (optQuote.isPresent()) {
			Quote quote = optQuote.get();
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.UK);
			Logger.consolLog("Requested amount: " + nf.format(loanAmount));
			Logger.consolLog("Rate: " + quote.getRate() + "%");
			Logger.consolLog("Monthly repayment: " + nf.format(quote.getMonthlyRepayment()));
			Logger.consolLog("Total repayment: " + nf.format(quote.getTotalRepayment()));
		} else {
			Logger.logInfo("Sorry, currently we can't provide a quote at that time.");
		}
	}

	private Integer parseLoanAmount(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			// The exception will be handled in the above layer
			return null;
		}
	}

}
