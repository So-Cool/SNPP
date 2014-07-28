package temperature;

import com.espertech.esper.client.EventBean;

public class CERNListener implements com.espertech.esper.client.UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        System.out.println("avg=" + event.get("avg(temperature)"));
	}
	
}
