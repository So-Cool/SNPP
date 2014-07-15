package testRun;

import com.espertech.esper.client.*;

// exampleMain
public class ex1 {
	
	private static final int windowLength = 2;
	
	public static int getWinLen() { return windowLength; }
 
    public static void main(String[] args) {
        //The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("StockTick", Tick.class.getName());
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
 
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL(
                // "select price from " +
                    // "StockTick(symbol='AAPL').win:length(2) as AAPL "+
                    // "where AAPL.price > 6"

                "select AAPLa.price, GOOGa.price from " +
                    "StockTick(symbol='AAPL').win:length(4) as AAPLa, "+
                    "StockTick(symbol='GOOG').win:length(4) as GOOGa "+
                    "having avg(AAPLa.price) > avg(GOOGa.price)"


                // "select price from " +
                //     "StockTick.win:length(1) where symbol='AAPL' as AAPLp, "+
                //     "StockTick.win:length(1) where symbol='GOOG' as GOOGp "+
                //     "having AAPLp > 0"


        		// "select avg(price) from StockTick(symbol='AAPL').win:length(" + getWinLen() + ") where price > 6.0"
                // "select price from StockTick(symbol='AAPL').win:length(" + getWinLen() + ") having avg(price) > 6.0"
        		);
 
        cepStatement.addListener(new CEPListener());
 
       // We generate a few ticks...
       // /**
          for(int i = 0; i < 5; i++) {
        	RandomEventGenerator.GenerateRandomTick(cepRT);
        }//*/
        
        // Generate a stream
        // while(true) {
        	// RandomEventGenerator.GenerateRandomTick(cepRT);
        // }
        
    }
}
