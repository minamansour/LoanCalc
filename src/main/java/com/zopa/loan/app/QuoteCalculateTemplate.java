package com.zopa.loan.app;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.zopa.loan.config.ParameterConfig;
import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;
import com.zopa.loan.util.Logger;

/**
 * @author Mina Mansour
 */
public abstract class QuoteCalculateTemplate {

	public final void calculate(ParameterConfig parameterConfig) throws IOException {
		try {
			List<Lender> lenders = getLenders(parameterConfig.getFilePath());

			Integer loanAmount = getLoanAmount(parameterConfig.getAmount());

			Optional<Quote> optQuote = getQuote(lenders, loanAmount);

			displayQuote(loanAmount, optQuote);

		} catch (NumberFormatException ex) {
			Logger.logError("The specified file can't be read. due to some added values can't be parsed {"
					+ ex.getMessage() + "}");
			throw ex;
		} catch (IllegalArgumentException ex) {
			Logger.logError(ex.getMessage());
			throw ex;
		} catch (IOException ex) {
			Logger.logError("The specified file doesn't exist.");
			throw ex;
		} catch (Exception ex) {
			Logger.logError(ex.getMessage());
			throw ex;
		}
	}

	public abstract List<Lender> getLenders(String cvsFile) throws NumberFormatException, IOException;

	public abstract Integer getLoanAmount(String loanAmountParam) throws IllegalArgumentException;

	public abstract Optional<Quote> getQuote(List<Lender> lenders, Integer loanAmount);

	public abstract void displayQuote(Integer loanAmount, Optional<Quote> optQuote);
}
