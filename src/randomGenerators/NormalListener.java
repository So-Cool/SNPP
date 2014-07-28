package randomGenerators;

import com.espertech.esper.client.EventBean;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

//	System.out.println("avg=" + newEvents[0].get("avg(current)"));
//  System.out.println("Event received: " + newEvents[0].getUnderlying());
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		
//		System.out.println("Normal Current=" + newEvents[0].get("current"));
		System.out.println("Normal Average=" + newEvents[0].get("NormAvgCur"));
		
//		if (  Double.parseDouble( newEvents[0].get("NormAvgCur").toString() ) > 100 )
//			System.out.println("Call me cuz it's: " + newEvents[0].get("NormAvgCur") );
	
	}
	
}
