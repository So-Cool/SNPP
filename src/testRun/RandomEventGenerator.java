package testRun;

import java.util.Random;
import com.espertech.esper.client.EPRuntime;

public class RandomEventGenerator {

    private static Random generator = new Random();
    
    // cumulative price
    private static int avg = 0;
    // previous tick price
    private static int previousPrice = 0;
    // number of ticks so far
    private static int no = 0;
    // frame window --- 1 or 2 depending on initialization
    private static int frame = 1;
    
    // define mean and std for normal distribution
    private static int mean = 50;
    private static int std = 100;
    
    public static void GenerateRandomTick(EPRuntime cepRT) {
 
        double price = (double) generator.nextInt(10);
        long timeStamp = System.currentTimeMillis();
        String symbol = "AAPL";
        Tick tick = new Tick(symbol, price, timeStamp);
        System.out.println("Sending tick:" + tick);
        
        // print the average
        avg = (int)price + previousPrice;
        no ++;
        System.out.println("Average so far: " + (double)(avg)/(double)(frame));
        
        cepRT.sendEvent(tick);

        // initialize to proper value after first tick
        frame = ex1.getWinLen();
        // initialize previous price
        previousPrice = (int)price;

    }
    
    
    // stream generators
    public static void stream1() {
    	while( true ) {
    		double s1 = (double) generator.nextGaussian() * (double)std + (double)mean;
    		if( s1 > 3*(double)mean/4) {
    			// send signal
    			double signal1 = (double) generator.nextInt(10);
    		}
    		//Pause for 4 seconds
            try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public static void stream2() {
    	while( true ) {
    		double s2 = (double) generator.nextGaussian() * (double)std*2 + (double)mean*2;
    		if( s2 > 3*(double)mean*2/4) {
    			// send signal
    			double signal2 = (double) generator.nextInt(20);
    		}
    		//Pause for 4 seconds
            try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    
}
