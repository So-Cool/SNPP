package engine;

import java.util.ArrayList;
import java.util.List;

import randomGenerators.Cosine;
import randomGenerators.MultivariateNormal;
import randomGenerators.Normal;
import randomGenerators.RG;
import randomGenerators.Sine;
import randomGenerators.Uniform;
import afinityPropagation.Afinity;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import featureExtractors.FeatureExtractor;
import featureExtractors.ListenerOne;


public class Driver {
	private static Boolean printout = false;
	
//	private static int time1 = 17;
//	private static int time2 = 122;
//	private static int time3 = 122;
	private static int time4 = 115;
//	private static int time5 = 113;
	
//	private static long mean = 123;
//	private static long variance = 17;
	
//	private static long lower = 155;
//	private static long upper = 177;
	
//	private static double[] means = {22, 123};
//	private static double[][] covariances = { {15, 1.33}, 
//											  {1.33, 17}
//											};
	
	private static double xSin = 1;
	private static double ySin = 1;

//	private static double xCos = 1;
//	private static double yCos = 1;
	
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
		ListenerOne List = new ListenerOne( clustering );
		// Choose EPL statement
		EPStatement features = FeatureExtractor.getStatement(epService, 1);
		features.addListener(List);

		////////////////////////////////////////////////////////////////////////////////////
		List<RG> threads = new ArrayList<RG>();
		RG t;
		
//		t = new Normal( mean, variance, time1, epService, printout );
//		threads.add(t);
//		( new Thread( (Normal)t ) ).start();
		
//		t = new Uniform( lower, upper, time2, epService, printout );
//		threads.add(t);
//		( new Thread( (Uniform)t ) ).start();
		
//		t = new MultivariateNormal( means, co-variances, time3, epService, printout );
//		threads.add(t);
//		( new Thread( (MultivariateNormal)t ) ).start();
		
		t = new Sine( xSin, ySin, time4, epService, printout );
		threads.add(t);
		( new Thread( (Sine)t ) ).start();
		
//		t = new Cosine( xCos, yCos, time5, epService, printout );
//		threads.add(t);
//		( new Thread( (Cosine)t ) ).start();
		
		GeneratorCSV[] writer = { List.getCsv() };
		String[] name = { List.getName() };
		
		( new Thread( new Killer( writer, name, threads ) ) ).start();
		
	}
	
}
