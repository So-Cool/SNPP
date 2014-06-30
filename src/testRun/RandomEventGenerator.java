package testRun;

import java.util.Random;
import com.espertech.esper.client.EPRuntime;

public class RandomEventGenerator {

    private static Random generator = new Random();
    
    public static void GenerateRandomTick(EPRuntime cepRT) {
 
        double price = (double) generator.nextInt(10);
        long timeStamp = System.currentTimeMillis();
        String symbol = "AAPL";
        Tick tick = new Tick(symbol, price, timeStamp);
        System.out.println("Sending tick:" + tick);
        cepRT.sendEvent(tick);
 
    }
	
}
