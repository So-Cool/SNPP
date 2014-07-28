package randomGenerators;

import com.espertech.esper.client.EventBean;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
//		System.out.println("avg=" + newEvents[0].get("avg(current)"));
		System.out.println("avg=" + newEvents[0].get("NormAvgCur"));
//        System.out.println("Event received: " + newEvents[0].getUnderlying());
	}
	
}
