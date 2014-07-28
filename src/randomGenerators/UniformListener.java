package randomGenerators;

import com.espertech.esper.client.EventBean;

public class UniformListener implements com.espertech.esper.client.UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Uniform Average=" + newEvents[0].get("UnifAvgCur"));
	}
	
}
