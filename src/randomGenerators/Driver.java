package randomGenerators;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Driver {
	private static int time1 = 5;
	private static int time2 = 122;
	private static int time3 = 122;
	private static int time4 = 115;
	private static int time5 = 113;
	
	private static long mean = 123;
	private static long variance = 17;
	
	private static long lower = 155;
	private static long upper = 177;
	
	private static double[] means = {22, 123};
	private static double[][] covariances = { {15, 1.33}, 
											  {1.33, 17}
											};
	
	private static double xSin = 1;
	private static double ySin = 1;

	private static double xCos = 1;
	private static double yCos = 1;
	
	public static double n10(double n) {
		return n-10;
	}
	
	public static void main(String[] args) {
		// Initialize ESPER config
		Configuration cepConfig = new Configuration();
//		cepConfig.addEventType("SinTick", Sine.class.getName());
//		cepConfig.addEventType("CosTick", Cosine.class.getName());
		cepConfig.addEventType("NormTick", Normal.class.getName());
//		cepConfig.addEventType("UnifTick", Uniform.class.getName());
//		cepConfig.addEventType("NmvtTick", MultivariateNormal.class.getName());
		
		// Initialize ESPER server
		EPServiceProvider epService = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig); //.getDefaultProvider();
		
		
//		String expression1 = "select avg(current) as NormAvgCur from NormTick.win:length(2)";
//			String expression1 = "select stddev(current) from NormTick.win:time(60 sec)";
		String expression1 = "select randomGenerators.Driver.n10(current) from NormTick";
//		String expression11 = "select current as NormCur from NormTick.win:length(2)";
		EPStatement statement1 = epService.getEPAdministrator().createEPL(expression1);
//		EPStatement statement11 = epService.getEPAdministrator().createEPL(expression11);
		////////////////////////////////////////////////////////////////
		NormalListener NormList = new NormalListener();
		statement1.addListener(NormList);
//		statement11.addListener(NormList);
		
		
//		String expression2 = "select avg(current) as UnifAvgCur from UnifTick.win:time(5 sec)";
//		EPStatement statement2 = epService.getEPAdministrator().createEPL(expression2);
//		////////////////////////////////////////////////////////////////
//		UniformListener UnifList = new UniformListener();
//		statement2.addListener(UnifList);
		
		
		( new Thread( new Normal( mean, variance, time1, epService ) ) ).start();
//		( new Thread( new Uniform( lower, upper, time2, epService ) ) ).start();	
//		( new Thread( new MultivariateNormal( means, covariances, time3, epService ) ) ).start();
//		
//		( new Thread( new Sine( xSin, ySin, time4, epService ) ) ).start();
//		( new Thread( new Cosine( xCos, yCos, time5, epService ) ) ).start();
	}
	
}
