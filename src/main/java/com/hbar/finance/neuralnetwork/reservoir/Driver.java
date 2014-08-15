package com.hbar.finance.neuralnetwork.reservoir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbar.finance.neuralnetwork.reservoir.committee.Committee;
import com.hbar.finance.neuralnetwork.reservoir.committee.CommitteeProperties;
import com.hbar.finance.neuralnetwork.reservoir.ReservoirProperties;
import com.hbar.finance.neuralnetwork.reservoir.tools.DataFetcher;
import com.hbar.finance.neuralnetwork.reservoir.tools.RCProperties;
import com.hbar.finance.neuralnetwork.reservoir.tools.StopWatch;


public class Driver {
    /*
	public static void main(String[] args) throws Exception {

        String csvFile = "src/main/resources/SP500.csv";

		List<double[]> input = DataFetcher.readConvertCSV(csvFile); //"C:\\Users\\Andrew Fishberg\\Desktop\\Supply Depot\\Financial Modeling\\SP500.csv");
		List<double[]> expect = new ArrayList<double[]>(input);
		//input.remove(input.size() - 1);
		//expect.remove(0);
		
		ReservoirProperties rp = new ReservoirProperties();
		rp.dimensions.input = 18;
		rp.dimensions.output = 18;
		//Reservoir res = new Reservoir(rp);
		//res.train(input, expect);
		
		CommitteeProperties cp = new CommitteeProperties();
		cp.dimensions.input = 18;
		cp.dimensions.output = 18;
		cp.properties.maxThreads = 5;
		Committee comm = new Committee(cp, rp);
		
		StopWatch t = new StopWatch();
		t.start();
		comm.trainMT(input, expect);
		t.stop();
		System.out.println(t);
		
		t.start();
		for (int i = 0; i < 15; i++) {
			double[] in = input.get(i);
			System.out.println(Arrays.toString(comm.exeMT(in)));
		}
		t.stop();
		System.out.println(t);
		
		//comm.getData().
		
		//System.out.println(Arrays.toString(input.get(0)));
		//System.out.println(input.get(0).length);
	}*/

    public static void main(String[] args) {
        String csvFile = "src/main/resources/SP500.csv";
        String propertiesFile = "src/main/resources/config.properties";

        if (args.length == 0 ) {
            printHelp();
        } else if (args[0].equalsIgnoreCase("random")) {
            randomColumnTraining(csvFile, propertiesFile);
        } else if (args[0].equalsIgnoreCase("columns")) {
            List<String> columns = new ArrayList<>();
            for (String arg : args) {
                System.out.println("received argument: " + arg);
                columns.add(arg);
            }
            columns.remove("columns");



        } else if (args[0].equalsIgnoreCase("genetic")) {
            System.out.println("NOT IMPLEMENTED YET");
        } else if (args[0].equalsIgnoreCase("grid")) {
            System.out.println("NOT IMPLEMENTED YET");
        } else {
            printHelp();
        }


        /*
        List<double[]> input = null;
        List<double[]> expect = null;

        try {
            RCProperties.getSystemProperties(propertiesFile);
        } catch (IOException e) {
            System.out.println("Exception occurred when reading in " + propertiesFile);
            e.printStackTrace();
        }


        try {
            input = DataFetcher.readConvertCSV(csvFile);
            expect = new ArrayList<double[]>(input);
        } catch (IOException | ParseException e) {
            System.out.println("Exception occurred when reading in " + csvFile);
            e.printStackTrace();
        }

        input = DataFetcher.randomizeList(input);

        ReservoirProperties rp = new ReservoirProperties();
        rp.dimensions.input = Integer.parseInt(System.getProperty("dimensions.input"));//18;
        rp.dimensions.output = Integer.parseInt(System.getProperty("dimensions.output"));//18;
        //Reservoir res = new Reservoir(rp);
        //res.train(input, expect);

        CommitteeProperties cp = new CommitteeProperties();
        cp.dimensions.input = Integer.parseInt(System.getProperty("dimensions.input"));//18;
        cp.dimensions.output = Integer.parseInt(System.getProperty("dimensions.output"));//18;
        cp.properties.maxThreads = Integer.parseInt(System.getProperty("properties.maxThreads"));//5;
        Committee comm = new Committee(cp, rp);

        StopWatch t = new StopWatch();
        t.start();
        comm.trainMT(input, expect);
        t.stop();
        System.out.println(t);

        t.start();

        for (int i = 0; i < Integer.parseInt(System.getProperty("properties.reservoirs")); i++) {
            double[] in = input.get(i);
            System.out.println(Arrays.toString(comm.exeMT(in)));
        }
        t.stop();
        System.out.println(t);*/

    }

    private static void randomColumnTraining(String csvFile, String propertiesFile) {

        csvFile = "src/main/resources/SP500.csv";
        propertiesFile = "src/main/resources/config.properties";
        List<double[]> input = null;
        List<double[]> expect = null;

        try {
            RCProperties.getSystemProperties(propertiesFile);
            sanityChecker();
        } catch (IOException e) {
            System.out.println("Exception occurred when reading in " + propertiesFile);
            e.printStackTrace();
        }


        try {
            input = DataFetcher.readConvertCSV(csvFile);
            expect = new ArrayList<double[]>(input);
        } catch (IOException | ParseException e) {
            System.out.println("Exception occurred when reading in " + csvFile);
            e.printStackTrace();
        }

        input = DataFetcher.randomizeList(input);

        ReservoirProperties rp = new ReservoirProperties();
        rp.dimensions.input = Integer.parseInt(System.getProperty("properties.random.dimensions.input"));//18;
        rp.dimensions.output = Integer.parseInt(System.getProperty("properties.random.dimensions.output"));//18;
        //Reservoir res = new Reservoir(rp);
        //res.train(input, expect);

        CommitteeProperties cp = new CommitteeProperties();
        cp.dimensions.input = Integer.parseInt(System.getProperty("properties.random.dimensions.input"));//18;
        cp.dimensions.output = Integer.parseInt(System.getProperty("properties.random.dimensions.output"));//18;
        cp.properties.maxThreads = Integer.parseInt(System.getProperty("properties.maxThreads"));//5;
        Committee comm = new Committee(cp, rp);

        StopWatch t = new StopWatch();
        t.start();
        comm.trainMT(input, expect);
        t.stop();
        System.out.println(t);

        t.start();

        for (int i = 0; i < Integer.parseInt(System.getProperty("properties.reservoirs")); i++) {
            double[] in = input.get(i);
            System.out.println(Arrays.toString(comm.exeMT(in)));
        }
        t.stop();
        System.out.println(t);

    }

    private static void printHelp() {

        System.out.println("You need to specify the type of training you");
        System.out.println("want to perform. The available options are: ");
        System.out.println("random, genetic(not implemented), grid(not implemented)");
        System.exit(0);
    }

    /**
     * Checks if the properties make sense and will not cause any errors.
     */
    private static void sanityChecker() {
        String input = System.getProperty("properties.random.dimensions.input");
        String output = System.getProperty("properties.random.dimensions.output");
        String columns = System.getProperty("properties.random.columns");

        if (!(input.equals(output) && output.equals(columns))) {
            System.out.println("WARNING: the matrix dimensions and the desired columns do not match.");
            System.out.println("Program will either fail or produce incorrect results");
            System.out.println("columns = " + columns);
            System.out.println("output = " + output);
            System.out.println("input = " + input);
        }
    }
}
