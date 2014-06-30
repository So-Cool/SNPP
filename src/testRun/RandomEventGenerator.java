package testRun;

import java.util.Random;
import com.espertech.esper.client.EPRuntime;

public class RandomEventGenerator {

    private static Random generator = new Random();
    
    // cumulative price
    private static int avg = 0;
    // number of ticks so far
    private static int no = 0;
    
    public static void GenerateRandomTick(EPRuntime cepRT) {
 
        double price = (double) generator.nextInt(10);
        long timeStamp = System.currentTimeMillis();
        String symbol = "AAPL";
        Tick tick = new Tick(symbol, price, timeStamp);
        System.out.println("Sending tick:" + tick);
        
        // print th eaverage
        avg += price;
        no ++;
        System.out.println("Average so far: " + (double)(avg)/(double)(no));
        
        cepRT.sendEvent(tick);
 
    }
	
}
