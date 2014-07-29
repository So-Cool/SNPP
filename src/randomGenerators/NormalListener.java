package randomGenerators;

import com.espertech.esper.client.EventBean;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

//	System.out.println("avg=" + newEvents[0].get("avg(current)"));
//  System.out.println("Event received: " + newEvents[0].getUnderlying());
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
//		Object all = newEvents[0].getUnderlying();
//		Object all1 = newEvents[1].getUnderlying();
		
//		System.out.println("Normal Average=" + newEvents[0].get("NormAvgCur"));
//		System.out.println("Normal Current=" + newEvents[1].get("NormCur"));
		
//		System.out.println("Normal Current=" + newEvents.length );
			System.out.println("Normal Current=" + newEvents[0].getUnderlying());
		
		//		System.out.println("lololo " + all1);
		
//		if (  Double.parseDouble( newEvents[0].get("NormAvgCur").toString() ) > 100 )
//			System.out.println("Call me cuz it's: " + newEvents[0].get("NormAvgCur") );
	
	}
	
}
