package org.g23.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

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
		double timeF=0;
		double latiF=0, longiF=0, accuracyF=0, confidenceF=50;
		
		for(int itr=i; itr<=j; itr++)
		{
			
			latiF += (double) data.getLocations().get(itr).getLatitudeE7() / (double) noOfPoints;
			longiF += (double) data.getLocations().get(itr).getLongitudeE7() / (double) noOfPoints;
			timeF += (double) data.getLocations().get(itr).getTimestampMs().getTime() / (double) noOfPoints;
			accuracyF += (double) data.getLocations().get(itr).getAccuracy()/ (double) noOfPoints;
			
			ArrayList<Activity> activities=(ArrayList<Activity>) data.getLocations().get(itr).getActivity();
			
			if(activities!=null)
			{
				confidenceF=0;
				int count=0;
				for (Activity activity : activities)
				{
					ArrayList<Activity_> activity_s=(ArrayList<Activity_>) activity.getActivity();
					for (Activity_ activity_ : activity_s)
					{
						if(activity_.getType().equals("STILL"))
						{
							count++;
						}
					}
				}
				
				for (Activity activity : activities)
				{
					ArrayList<Activity_> activity_s=(ArrayList<Activity_>) activity.getActivity();
					for (Activity_ activity_ : activity_s)
					{
						if(activity_.getType().equals("STILL"))
						{
							confidenceF+= (double) activity_.getConfidence()/ (double) count;
						}
					}
				}
			}
		}
		
		int lati = Double.valueOf(latiF).intValue();
		int longi = Double.valueOf(longiF).intValue();
		int time = Double.valueOf(timeF).intValue();
		int accuracy = Double.valueOf(accuracyF).intValue();
		int confidence = Double.valueOf(confidenceF).intValue();
		
		Activity_ activity_=new Activity_("STILL",confidence);
		
		ArrayList<Activity_> activities_ = new ArrayList<Activity_>();
		activities_.add(activity_);
		
		Activity activity=new Activity(new Timestamp(time),activities_);
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		activities.add(activity);
		
		StayPoint point=new StayPoint(new Timestamp(time), lati, longi, accuracy, data.getLocations().get(i).getTimestampMs(), data.getLocations().get(j).getTimestampMs(), activities);
		
		return point;
	}
}
