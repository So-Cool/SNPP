package featureExtractors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import afinityPropagation.Afinity;

import com.espertech.esper.client.EventBean;

import engine.GeneratorCSV;

public class ListenerOne implements com.espertech.esper.client.UpdateListener {

	private String name = "NormalListener";
	private Afinity clustering;
	private GeneratorCSV csv;
	private int features;
	private DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
//			new SimpleDateFormat();
	
	
	public ListenerOne( Afinity cls, int feats ) {
		this.clustering = cls;
		this.features = feats;
		// Create CSV writer
		csv = new GeneratorCSV(name);
		csv.header( "AvgCur,StdCur,LagICur,LagIICur,CurCur,ThrCur,TimeCur" );
	}
	
	// Get the CSV handler back
	public GeneratorCSV getCsv() { return this.csv; }
	// Get the name back
	public String getName() { return this.name; }
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Features=" + newEvents[0].getUnderlying()); // get("NormAvgCur") is *Object* // length
		
		// Extract features
		double AvgCur   = Double.parseDouble( newEvents[0].get("AvgCur").toString() );
		double StdCur   = (newEvents[0].get("StdCur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("StdCur").toString() );
		double LagICur  = (newEvents[0].get("Lag1Cur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("Lag1Cur").toString() );
		double LagIICur = (newEvents[0].get("Lag2Cur") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("Lag2Cur").toString() );
		double CurCur   = Double.parseDouble( newEvents[0].get("CurCur").toString() );
		double ThrCur   = Double.parseDouble( newEvents[0].get("thrCur").toString() );
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
		double[] x = {AvgCur, StdCur, LagICur, LagIICur, CurCur, ThrCur};
		// or better send them to channel
		long ts = -1;
		try {
			ts = dateFormat.parse(TimeCur).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clustering.getPoint( Arrays.copyOfRange(x, 0, features), ts);
		//	clustering.getPoint(TimeCur);
		System.out.println( AvgCur + "," + StdCur + "," + LagICur + "," + LagIICur + "," + CurCur + "," + ThrCur + "," + TimeCur );
	}
	
}
