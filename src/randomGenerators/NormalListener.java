package randomGenerators;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;

import engine.Afinity;
import engine.GeneratorCSV;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

	private Afinity clustering;
	private EPServiceProvider epService;
	private GeneratorCSV csv;
	
	
	private int features;
	private String expression;
	private EPStatement statement;
	
	/////////////////////////////features extractors///////////////////////////////
	public static int threshold(double n) {
		if( n > 100 )
			return 1;
		else
			return 0;
	}
	public static int gradient(double n) {
		if( n < 25 )
			return 0;
		else if( n < 50 )
			return 1;
		else if( n < 75 )
			return 2;
		else if( n < 100 )
			return 3;
		else if( n < 125 )
			return 4;
		else if( n < 150 )
			return 5;
		else
			return 6;
	}
	///////////////////////////////////////////////////////////////////////////////
	
	
	public NormalListener( EPServiceProvider epsp, Afinity cls, int type ) {
		this.clustering = cls;
		this.epService = epsp;

		switch (type) {
		case 1: this.expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
					"(current - prev(2, current)) as Lag2Cur, current as CurCur, randomGenerators.NormalListener.threshold(current) as thrCur, "+
					"timer as TimeCur from NormTick.win:time(60 sec)";
				this.features = 7;
				break;
		case 2: this.expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
					"(current - prev(2, current)) as Lag2Cur, current as CurCur, randomGenerators.NormalListener.threshold(current) as thrCur, "+
					"timer as TimeCur from NormTick.win:time_batch(1 min)";
				this.features = 7;
				break;
		case 3: this.expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
					"(current - prev(2, current)) as Lag2Cur, current as CurCur, randomGenerators.NormalListener.threshold(current) as thrCur, "+
					"timer as TimeCur from NormTick.win:length(10)";
				this.features = 7;
				break;
		}

		this.statement = epService.getEPAdministrator().createEPL(expression);
		
		// Create CSV writer
		csv = new GeneratorCSV("NormalListener");
		csv.header( "AvgCur,StdCur,LagICur,LagIICur,CurCur,ThrCur,TimeCur" );
		
		// Acknowledge clustering algorithm of features number
		clustering.getSome(features);
	}
	
	// Get the statement back
	public EPStatement getStatement() { return this.statement; }
	
	// Get the CSV handler back
	public GeneratorCSV getCsv() { return this.csv; }
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Features=" + newEvents[0].getUnderlying()); // get("NormAvgCur") is *Object* // length
		
		// Extract features
		double AvgCur   = Double.parseDouble( newEvents[0].get("AvgCur").toString() );
		double StdCur   = (newEvents[0].get("StdCur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("StdCur").toString() );
		double LagICur  = (newEvents[0].get("Lag1Cur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("Lag1Cur").toString() );
		double LagIICur = (newEvents[0].get("Lag2Cur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("Lag2Cur").toString() );
		double CurCur   = Double.parseDouble( newEvents[0].get("CurCur").toString() );
		int    ThrCur   = Integer.parseInt( newEvents[0].get("thrCur").toString() );
		String TimeCur  = newEvents[0].get("TimeCur").toString();
		
		// Write features to file
		csv.element( newEvents[0].get("AvgCur").toString() );
		csv.comma();
		csv.element( (newEvents[0].get("Lag1Cur") == null) ? "?" : newEvents[0].get("StdCur").toString() );
		csv.comma();
		csv.element( (newEvents[0].get("Lag1Cur") == null) ? "?" : newEvents[0].get("Lag1Cur").toString() );
		csv.comma();
		csv.element( (newEvents[0].get("Lag2Cur") == null) ? "?" :  newEvents[0].get("Lag2Cur").toString() );
		csv.comma();
		csv.element( newEvents[0].get("CurCur").toString() );
		csv.comma();
		csv.element( newEvents[0].get("thrCur").toString() );
		csv.comma();
		csv.element( newEvents[0].get("TimeCur").toString() );
		csv.newLine();
		
		// Send features to clustering algorithm
		clustering.getSome(AvgCur);
		clustering.getSome(StdCur);
		clustering.getSome(LagICur);
		clustering.getSome(LagIICur);
		clustering.getSome(CurCur);
		clustering.getSome(ThrCur);
		clustering.getSome(TimeCur);
		System.out.println( AvgCur + "," + StdCur + "," + LagICur + "," + LagIICur + "," + CurCur + "," + ThrCur + "," + TimeCur );
	}
	
}
