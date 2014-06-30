package testRun;

import com.espertech.esper.client.*;

// exampleMain
public class ex1 {
 
    public static void main(String[] args) {
 
        //The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("StockTick", Tick.class.getName());
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
 
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from " +
                "StockTick(symbol='AAPL').win:length(2) " +
                "having avg(price) > 6.0");
 
        cepStatement.addListener(new CEPListener());
 
       // We generate a few ticks...
        for (int i = 0; i < 5; i++) {
        	RandomEventGenerator.GenerateRandomTick(cepRT);
        }
    }
}
