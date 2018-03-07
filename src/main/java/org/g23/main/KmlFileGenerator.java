package org.g23.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

import org.g23.entities.json.out.StayPoint;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class KmlFileGenerator
{
	private final int convFactor=-7;
	
	@SuppressWarnings(value = { "" })
	public void generateKML(HashSet<StayPoint> stayPoints) throws FileNotFoundException
	{
		File kmlCoordinatesFile=new File("/home/nirmal/Documents/Final-Year-Project/Stay_Points.kml");
		Kml kml= new Kml();
		Document document = kml.createAndSetDocument().withName("Stay Points KML").withOpen(true);
		
		int i=0;
		StringBuilder stayPointName = new StringBuilder("Stay Point ");
		
		for (StayPoint stayPoint : stayPoints)
		{
			double longi=stayPoint.getLongitudeE7()*Math.pow(10, convFactor);
			double lati=stayPoint.getLatitudeE7()*Math.pow(10, convFactor);
			
			document.createAndAddPlacemark().withName(stayPointName+(String.valueOf(i)).toString()).withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(longi, lati).setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
			i++;
		}
		kml.marshal(kmlCoordinatesFile);
		System.out.println("Stay Points(KML) at : /home/nirmal/Documents/Final-Year-Project/Stay_Points.json");
	}
}
