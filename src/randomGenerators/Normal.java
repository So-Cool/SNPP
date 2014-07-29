package randomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import com.espertech.esper.client.EPServiceProvider;

public class Normal implements Runnable {

	// generate timeToWait according to Poisson distribution
	private NormalDistribution gen;
	private Date timer;
	private PoissonDistribution elaps;
	private double current;
	private String genName = "Normal";
	
	// ESPER service provider
	private EPServiceProvider myService;
	
	public Normal( long mean, long variance, int inter, EPServiceProvider service ) {
		this.gen = new NormalDistribution( mean, variance );
		this.elaps = new PoissonDistribution(inter);
		this.timer = new Date( System.currentTimeMillis() );
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	  public Normal(Normal another) {
		    this.gen = another.gen;
		    this.timer = another.timer;
		    this.elaps = another.elaps;
		    this.current = another.current;
		    this.genName = another.genName;
		  }
	
	public void gen() {
		this.current = this.gen.sample();
		this.timer.setTime( System.currentTimeMillis() );
	}
	public long waita() {
		return this.elaps.sample();
	}
	
	public Date getTimer() { return this.timer; }
	public double getCurrent() { return this.current; }
	
	// Define what to do in the thread
	public void run() {
		long timeToWait = 0;
		
		while(true) {
			timeToWait = this.waita();
			this.gen();
			
			// Print and send tick
			System.out.println( this.toString() );
			// once updated send
			myService.getEPRuntime().sendEvent( new Normal(this) );
			
			
			//Pause for timeToWait seconds
			try {
				Thread.sleep( timeToWait * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Return string
    @Override
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
	
}
