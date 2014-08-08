package randomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import com.espertech.esper.client.EPServiceProvider;

public class MultivariateNormal extends RG{

	// generate timeToWait according to Poisson distribution
	private MultivariateNormalDistribution gen;
	
	private double[] currents;
	
	public MultivariateNormal( double[] means, double[][] covariances, int inter, EPServiceProvider service, Boolean print ) {
		genName = "Multivariate Normal";
		this.gen = new MultivariateNormalDistribution( means, covariances );
		this.elaps = new PoissonDistribution(inter);
		this.timer = new Date( System.currentTimeMillis() );
		this.printOut = print;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	public MultivariateNormal(MultivariateNormal another) {
	   this.gen = another.gen;
	   this.timer = another.timer;
	   this.elaps = another.elaps;
	   this.currents = another.currents;
	   this.myService = another.myService;
	   this.printOut = another.printOut;
	}
	  
	public void gen() {
		this.currents = this.gen.sample();
		this.timer.setTime( System.currentTimeMillis() );
	}
	
	// Define what to do in the thread
	@Override
	public void run() {
		long timeToWait = 0;
		
		while( running ) {
			timeToWait = waita();
			
			this.gen();
			
			// Print and send tick
			if( printOut )
				System.out.println( toString() );
			// once updated send
			myService.getEPRuntime().sendEvent(this);
			
			//Pause for timeToWait seconds
			try {
				Thread.sleep( timeToWait * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		closer();
	}
	
	public double[] getCurrents() { return this.currents; }
	
	// Return string
    @Override
    public String toString() {
    	String list = "(";
    	for(int i = 0; i < currents.length; i++) {
            list += currents[i];
            if( i+1 != currents.length )
            	list += ", ";
        }
    	list += ")";
    	
        return "Tick! -> Generator: " + genName + " | Sample: " + list + " | Time: " + timer;
    }
	
}
