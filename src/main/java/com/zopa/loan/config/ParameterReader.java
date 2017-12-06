package com.zopa.loan.config;

/**
 * @author Mina Mansour
 */
public class ParameterReader {

	private static ParameterReader parameterReader = null;

	private ParameterReader() {
	}

	public static ParameterReader getInstance() {
		if (null == parameterReader) {
			parameterReader = new ParameterReader();
		}
		return parameterReader;
	}

	public ParameterConfig readParameter(String... args) throws IllegalArgumentException {
		ParameterConfig parameterConfig = new ParameterConfig();
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: QuoteApp [path_to_csv_file] [loan_amount]");
		}

		// Assuming that the file path will be the first parameter
		if (null != args[0]) {
			parameterConfig.setFilePath(args[0]);
		}
		if (null != args[1]) {
			parameterConfig.setAmount(args[1]);
		}
		
		return parameterConfig;
	}

}
