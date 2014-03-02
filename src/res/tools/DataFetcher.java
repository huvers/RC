package res.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public abstract class DataFetcher {

	/**
	 * Reads a CSV file and read and returns it in a List.
	 * 
	 * @param fileName
	 *            - File to read
	 * @return - List of CSV file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> readCSV(String fileName) throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));

		List<String[]> lst = new ArrayList<String[]>();
		String[] curr;
		while ((curr = reader.readNext()) != null) {
			lst.add(curr);
		}
		
		reader.close();
		return lst;
	}

	/**
	 * Converts a list of strings arrays from a CSV file into a list of double
	 * arrays for data processing
	 * 
	 * @param original
	 * @return
	 * @throws ParseException
	 */
	public static List<double[]> convertData(List<String[]> original) throws ParseException {
		List<double[]> converted = new ArrayList<double[]>();
		
		final int COLS_KEEP = 18;
		
		try {
			for (String[] row : original) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				long l = formatter.parse(row[0]).getTime() / 1000; // UNIX TIMESTAMP
				double[] arr = new double[COLS_KEEP];
				for (int i = 0; i < COLS_KEEP; i++) {
					arr[i] = Double.parseDouble(row[i + 1]);
				}
				converted.add(arr);
			}

			return converted;
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * Reads a file and converts its data into a list of double arrays
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<double[]> readConvertCSV(String fileName) throws FileNotFoundException, IOException, ParseException {
		try {
			List<String[]> lstStr = readCSV(fileName); // read file
			lstStr.remove(0); // remove the column titles
			List<double[]> lstDbl = convertData(lstStr); // converted file
			return lstDbl;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		}
	}
}
