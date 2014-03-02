package res.committee;

public class CommitteeProperties {
	public static class Dimensions {
		public int input = 1;
		public int output = 1;
	}
	
	public static class Properties {
		public int members = 10;
		public int maxThreads = 1; // should be less than or equal to members and number of processors
	}
	
	public Dimensions dimensions = new Dimensions();
	public Properties properties = new Properties();
}
