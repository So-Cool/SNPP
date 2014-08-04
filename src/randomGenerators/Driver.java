package randomGenerators;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import engine.Afinity;
import engine.GeneratorCSV;

public class Driver {
	private static Boolean printout = false;
	
	private static int time1 = 17;
	/*private static int time2 = 122;
	private static int time3 = 122;
	private static int time4 = 115;
	private static int time5 = 113;*/
	
	private static long mean = 123;
	private static long variance = 17;
	
	/*private static long lower = 155;
	private static long upper = 177;
	
	private static double[] means = {22, 123};
	private static double[][] covariances = { {15, 1.33}, 
											  {1.33, 17}
											};
	
	private static double xSin = 1;
	private static double ySin = 1;

	private static double xCos = 1;
	private static double yCos = 1;*/
	
	public static void main(String[] args) {
		// Initialize ESPER configuration
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("SinTick", Sine.class.getName());
		cepConfig.addEventType("CosTick", Cosine.class.getName());
		cepConfig.addEventType("NormTick", Normal.class.getName());
		cepConfig.addEventType("UnifTick", Uniform.class.getName());
		cepConfig.addEventType("NmvtTick", MultivariateNormal.class.getName());
		
		// Initialize ESPER server
		EPServiceProvider epService = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig); //.getDefaultProvider();
		
		
		// Create a clustering instance
		Afinity clustering = new Afinity( 1, 1, 1.0, null );
		
		// create new listener
		NormalListener NormList = new NormalListener( epService, clustering, 1 );
		NormList.getStatement().addListener(NormList);


		( new Thread( new Normal( mean, variance, time1, epService, printout ) ) ).start();
//		( new Thread( new Uniform( lower, upper, time2, epService, printout ) ) ).start();	
//		( new Thread( new MultivariateNormal( means, co-variances, time3, epService, printout ) ) ).start();
//		( new Thread( new Sine( xSin, ySin, time4, epService, printout ) ) ).start();
//		( new Thread( new Cosine( xCos, yCos, time5, epService, printout ) ) ).start();
		
		
		// close file writing on exit
		final GeneratorCSV writer = NormList.getCsv();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	System.out.println("\nClosing file writer.");
		    	writer.close();
		    	// Do WEKA comparison
		    }
		}));
		
	}
	
}
