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
	
	private static double noise1 = 0;
//	private static double noise2 = 0;
	
	private static double xSin = 1;
	private static double ySin = 1;

//	private static double xCos = 1;
//	private static double yCos = 1;
	
	public static String getStatement( int type ) {

		String expression = "";

		switch (type) {
			case 1: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:time(60 sec)";
					break;
			case 2: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:time_batch(1 min)";
					break;
			case 3: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:length(10)";
					break;
		}

		return expression;
	}
	
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
		
		
		// Create a clustering instance ( reservir size, initial sample size, p-Value, lambda factor, cpc test size )
		Afinity clustering = new Afinity( 100, 75, 1.0, 2.0, 20 );
		
		// create new listener
		ListenerOne List = new ListenerOne( clustering, 7 );
		// Choose EPL statement
		
		EPStatement features = epService.getEPAdministrator().createEPL( getStatement(1) );
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
		
		t = new Sine( xSin, ySin, time4, noise1, epService, printout );
		threads.add(t);
		( new Thread( (Sine)t ) ).start();
		
//		t = new Cosine( xCos, yCos, time5, noise2, epService, printout );
//		threads.add(t);
//		( new Thread( (Cosine)t ) ).start();
		
		GeneratorCSV[] writer = { List.getCsv() };
		String[] name = { List.getName() };
		
		( new Thread( new Killer( writer, name, threads ) ) ).start();
		
	}
	
}
