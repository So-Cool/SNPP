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
	
}
