package com.zopa.loan.app;

import com.zopa.loan.config.ParameterConfig;
import com.zopa.loan.config.ParameterReader;

/**
 * A command line based tool to produce a quote based on a csv file containing a
 * list of lenders and a loan amount.
 *
 * @author Mina Mansour
 */
public class QuoteApp {

	public static void main(String[] args) {

		try {
			ParameterConfig parameterConfig = ParameterReader.getInstance().readParameter(args);
			QuoteCalculateTemplate quoteCalculate = new QuoteCalculator();
			quoteCalculate.calculate(parameterConfig);
			System.exit(0);
		} catch (Exception ex) {
			System.exit(1);
		}

	}

}
