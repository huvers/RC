package res;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import res.committee.Committee;
import res.committee.CommitteeProperties;
import res.reservoir.ReservoirProperties;
import res.tools.DataFetcher;
import res.tools.StopWatch;


public class Driver {
	public static void main(String[] args) throws Exception {
		List<double[]> input = DataFetcher.readConvertCSV("C:\\Users\\Andrew Fishberg\\Desktop\\Supply Depot\\Financial Modeling\\SP500.csv");
		List<double[]> expect = new ArrayList<double[]>(input);
		input.remove(input.size() - 1);
		expect.remove(0);
		
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
		for (int i = 0; i < 10; i++) {
			double[] in = input.get(i);
			System.out.println(Arrays.toString(comm.exeMT(in)));
		}
		t.stop();
		System.out.println(t);
		
		//comm.getData().
		
		//System.out.println(Arrays.toString(input.get(0)));
		//System.out.println(input.get(0).length);
	}
}
