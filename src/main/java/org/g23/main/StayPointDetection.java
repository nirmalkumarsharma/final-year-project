package org.g23.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.g23.calc.CalculateDistance;
import org.g23.calc.CalculateTimeDifference;
import org.g23.entities.json.Activity;
import org.g23.entities.json.Activity_;
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
	private static LocationData data;
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
	{
		File file=new File("/home/nirmal/Downloads/Location_History.json");
		ObjectMapper mapper=new ObjectMapper();
		data=mapper.readValue(file, LocationData.class);
		
		int i=0;
		int pointNum=data.getLocations().size();
		
		CalculateDistance gpsistance=new CalculateDistance();
		CalculateTimeDifference timeDiff=new CalculateTimeDifference();
		
		ArrayList<StayPoint> stayPoints=new ArrayList<StayPoint>();
		
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
						stayPoints.add(computeMeanPoint(i, j));
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
	
	private static StayPoint computeMeanPoint(int i, int j)
	{
		int noOfPoints=j-i+1;
		long time=0,delTime=0;
		int lati=0, longi=0, accuracy=0, delLati=0, delLongi=0, delAccuracy=0;
		
		for(int itr=i; itr<=j; itr++)
		{
			lati+=data.getLocations().get(itr).getLatitudeE7()/noOfPoints;
			delLati+=data.getLocations().get(itr).getLatitudeE7()%noOfPoints;
			
			longi+=data.getLocations().get(itr).getLongitudeE7()/noOfPoints;
			delLongi+=data.getLocations().get(itr).getLongitudeE7()%noOfPoints;
			
			time+=data.getLocations().get(itr).getTimestampMs().getTime()/noOfPoints;
			delTime+=data.getLocations().get(itr).getTimestampMs().getTime()%noOfPoints;
			
			accuracy+=data.getLocations().get(itr).getAccuracy()/noOfPoints;
			delAccuracy+=data.getLocations().get(itr).getAccuracy()%noOfPoints;
			
		}
		
		lati+=delLati/noOfPoints;
		longi+=delLongi/noOfPoints;
		time+=delTime/noOfPoints;
		accuracy+=delAccuracy;
		
		
		Activity_ activity_=new Activity_("STILL",100-accuracy);
		
		ArrayList<Activity_> activities_ = new ArrayList<Activity_>();
		activities_.add(activity_);
		
		Activity activity=new Activity(new Timestamp(time),activities_);
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(activity);
		
		StayPoint point=new StayPoint(new Timestamp(time), lati, longi, accuracy, data.getLocations().get(i).getTimestampMs(), data.getLocations().get(j).getTimestampMs(), activities);
		
		return point;
	}
}
