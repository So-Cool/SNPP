package randomGenerators;

import testRun.CEPListener;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Driver {
	private static int time1 = 222;
	private static int time2 = 122;
	private static int time3 = 22;
	private static int time4 = 15;
	private static int time5 = 3;
	
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
	
	public static void main(String[] args) {
		// Initialize ESPER server
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		String expression = "select temperature from temperature.CERNtermometer.win:time(5 sec) having avg(temperature) > 5.0";
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		
//		CERNListener listener = new CERNListener();
//		statement.addListener(listener);
		statement.addListener(new CEPListener());
		
		
		( new Thread( new Normal( mean, variance, time1, epService ) ) ).start();
		( new Thread( new Uniform( lower, upper, time2, epService ) ) ).start();	
		( new Thread( new MultivariateNormal( means, covariances, time3, epService ) ) ).start();
		
		( new Thread( new Sine( xSin, ySin, time4, epService ) ) ).start();
		( new Thread( new Cosine( xCos, yCos, time5, epService ) ) ).start();
	}
	
}
