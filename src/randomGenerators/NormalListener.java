package randomGenerators;

import com.espertech.esper.client.EventBean;

import engine.Afinity;
import engine.GeneratorCSV;

public class NormalListener implements com.espertech.esper.client.UpdateListener {

	private Afinity clustering;
	private GeneratorCSV csv;
	
	public NormalListener( Afinity cls ) {
		this.clustering = cls;
		// Create CSV writer
		csv = new GeneratorCSV("NormalListener");
		csv.header( "AvgCur,StdCur,LagICur,LagIICur,CurCur,ThrCur,TimeCur" );
	}
	
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
