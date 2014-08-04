package randomGenerators;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;

import engine.Afinity;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

	private Afinity clustering;
	private EPServiceProvider epService;
	
	
	private int features;
	private String expression;
	private EPStatement statement;
	
	/////////////////////////////features extractors///////////////////////////////
	public static double n10(double n) {
		return n-10;
	}
///////////////////////////////////////////////////////////////////////////////
	
	
	public NormalListener( EPServiceProvider epsp, Afinity cls, int type ) {
		this.clustering = cls;
		this.epService = epsp;

		switch (type) {
		case 1: this.expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
					"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.n10(current) as n10Cur "+
					"from NormTick.win:time(30 sec)";
				this.features = 5;
				break;
		case 2: this.expression = "select avg(current) as NormAvgCur from NormTick.win:time(30 sec)";
				this.features = 5;
				break;
		case 3: this.expression = "select avg(current) as NormAvgCur from NormTick.win:length(30)";
				this.features = 5;
				break;
		}

		this.statement = epService.getEPAdministrator().createEPL(expression);
	}
	
	// Get the statement back
	public EPStatement getStatement() { return this.statement; }
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Normal Current=" + newEvents[0].getUnderlying()); // get("NormAvgCur") is *Object* // length
		//if (  Double.parseDouble( newEvents[0].get("NormAvgCur").toString() ) > 100 )
		
		// Write features to file
		
		// Send features to clustering algorithm
	}
	
}
