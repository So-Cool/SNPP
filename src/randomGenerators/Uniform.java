package randomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import com.espertech.esper.client.EPServiceProvider;

public class Uniform extends RG{

	// generate timeToWait according to Poisson distribution
	private UniformRealDistribution gen;
	
	public Uniform( long lower, long upper, int inter, EPServiceProvider service, Boolean print ) {
		genName = "Uniform Real";
		this.gen = new UniformRealDistribution( lower, upper );
		this.elaps = new PoissonDistribution(inter);
		this.timer = new Date( System.currentTimeMillis() );
		this.printOut = print;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	public Uniform(Uniform another) {
	   this.gen = another.gen;
	   this.timer = another.timer;
	   this.elaps = another.elaps;
	   this.current = another.current;
	   this.myService = another.myService;
	   this.printOut = another.printOut;
	}
	
	public void gen() {
		this.current = this.gen.sample();
		this.timer.setTime( System.currentTimeMillis() );
	}
	
	// Define what to do in the thread
	@Override
	public void run() {
		long timeToWait = 0;
		
		while( running ) {
			timeToWait = this.waita();
			
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
	
}
