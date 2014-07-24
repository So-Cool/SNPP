package RandomGenerators;

public class Driver {
	private static int time1 = 222;
	private static int time2 = 122;
	private static int time3 = 22;
	
	private static long mean = 123;
	private static long variance = 17;
	
	private static long lower = 155;
	private static long upper = 177;
	
	private static double[] means = {22, 123};
	private static double[][] covariances = { {15, 1.33}, 
											  {1.33, 17}
											};
	
	public static void main(String[] args) {
		( new Thread( new Normal( mean, variance, time1 ) ) ).start();
		( new Thread( new Uniform( lower, upper, time2 ) ) ).start();	
		( new Thread( new MultivariateNormal( means, covariances, time3 ) ) ).start();
	}
	
}
