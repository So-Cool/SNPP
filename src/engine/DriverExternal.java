package engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import randomGenerators.*;
import afinityPropagation.Afinity;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.espertech.esper.client.deploy.ParseException;

import featureExtractors.ListenerFeatures;

public class DriverExternal {
	private static Boolean printout = false;
	
//	private static int time1 = 17;
//	private static int time2 = 122;
//	private static int time3 = 122;
	private static int time4 = 5;
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
	
	public static void main(String[] args) throws IOException, ParseException, DeploymentException {
		// Initialize ESPER server
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		
		// Create external files --- deployment module
		EPDeploymentAdmin deployAdmin = epService.getEPAdministrator().getDeploymentAdmin();
		Module module = deployAdmin.read(new File("rules.EPL"));
		// Make the module know; It now shows up in undeployed state
		String moduleDeploymentId = deployAdmin.add(module);
		// Start all statements, passing a null options object for default options
		deployAdmin.deploy(moduleDeploymentId, null);
		
		// Create a clustering instance ( reservoir size, initial sample size, p-Value, lambda factor, cpc test size )
		Afinity clustering = new Afinity( 100, 75, 1.0, 2.0, 20 );
		
		// create new listener
		ListenerFeatures ListFea = new ListenerFeatures( clustering );
		
		epService.getEPAdministrator().getStatement("Time-frame-fea").addListener( ListFea );

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
		
		GeneratorCSV[] writer = { ListFea.getCsv() };
		String[] name = { ListFea.getName() };
		
		( new Thread( new Killer( writer, name, threads ) ) ).start();
		
	}
	
	
}
