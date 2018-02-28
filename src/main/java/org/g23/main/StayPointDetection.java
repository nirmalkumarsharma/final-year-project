package org.g23.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashSet;

import org.g23.calc.CalculateDistance;
import org.g23.calc.CalculateTimeDifference;
import org.g23.calc.ComputeMean;
import org.g23.entities.json.LocationData;
import org.g23.entities.json.StayPoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class StayPointDetection
{
	private static final double distThresh = 0.1; /* In Kilometers */
	private static final long timeThresh = 15; /* In Minutes */
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
	{
		File file=new File("/home/nirmal/Downloads/Location_History.json");
		ObjectMapper mapper=new ObjectMapper();
		LocationData data=mapper.readValue(file, LocationData.class);
		ComputeMean computeMean=new ComputeMean();
		
		int i=0;
		int pointNum=data.getLocations().size();
		
		CalculateDistance gpsistance=new CalculateDistance();
		CalculateTimeDifference timeDiff=new CalculateTimeDifference();
		
		HashSet<StayPoint> stayPoints=new HashSet<StayPoint>();
		
		while(i<pointNum)
		{
			int j = i + 1 ;
			int Token = 0 ;
			while(j < pointNum)
			{
				int lat1 = data.getLocations().get(i).getLatitudeE7();
				int long1 = data.getLocations().get(i).getLongitudeE7();
				int lat2 = data.getLocations().get(j).getLatitudeE7();
				int long2 = data.getLocations().get(j).getLatitudeE7();
				
				double dist=gpsistance.calculateDistance(long1, lat1, long2, lat2, "K");
				
				if(dist>distThresh)
				{
					Timestamp timestamp1=data.getLocations().get(i).getTimestampMs();
					Timestamp timestamp2=data.getLocations().get(j).getTimestampMs();
					long timeSpan=timeDiff.compareTwoTimeStamps(timestamp1, timestamp2);
					
					if(timeSpan>timeThresh)
					{
						stayPoints.add(computeMean.computeMeanPoint(i, j,data));
						i=j;
						Token=1;
					}
					break;
				}
				j++;
			}
			if(Token != 1)
			{
				i=i+1;
			}
		}
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String stayPointFile = ow.writeValueAsString(stayPoints);
		PrintWriter out = new PrintWriter("/home/nirmal/Downloads/Stay_Point.json");
		out.print(stayPointFile);
		out.close();
		System.out.println("Stay Points at : /home/nirmal/Downloads/Stay_Point.json");
	}
}
