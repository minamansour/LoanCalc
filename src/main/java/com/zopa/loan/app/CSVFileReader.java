package com.zopa.loan.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zopa.loan.model.Lender;

/**
 * <p>
 * Services that produces a list of {@link Lender} instances based on a CSV
 * file.
 * </p>
 *
 * @author Mina Mansour
 */
public class CSVFileReader {

	private static final String SEPARATOR = ",";

	/**
	 * <p>
	 * Parses a CSV file, and returns a list of {@link Lender} instances.
	 * </p>
	 *
	 * @param filePath
	 *            Path to the csv file.
	 * @return lenders List of {@link Lender} instances
	 * @throws IOException
	 *             if a problem has been produced while reading the file.
	 */
	public List<Lender> processFile(String filePath) throws IOException, NumberFormatException {

		List<Lender> lenders = new ArrayList<Lender>();
		BufferedReader bufferedReader = null;

		try {

			bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
			String line = "";

			// Skip the header
			bufferedReader.readLine();

			// Start reading from the second line
			while ((line = bufferedReader.readLine()) != null) {
				String[] tokens = line.split(SEPARATOR);

				if (tokens.length > 0) {
					// Save the lender details.
					Lender lender = new Lender(tokens[0], new BigDecimal(Float.parseFloat(tokens[1])),
							Integer.parseInt(tokens[2]));
					lenders.add(lender);
				}
			}
		} finally {
			if (null != bufferedReader) {
				bufferedReader.close();
			}
		}

		return lenders;
	}
}
