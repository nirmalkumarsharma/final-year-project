package org.g23.calc;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.g23.entities.json.in.Activity;
import org.g23.entities.json.in.Activity_;
import org.g23.entities.json.in.LocationData;
import org.g23.entities.json.out.StayPoint;

public class ComputeMean
{
	public StayPoint computeMeanPoint(int i, int j, LocationData data)
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
