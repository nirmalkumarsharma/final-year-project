package org.g23.calc;

public class GPSCoordinateDistance
{
	public double gpsDistance(double long1, double lat1, double long2, double lat2, String unit)
	{
		double theta = long1 - long2;
		double dist = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
		dist = Math.acos(dist);
		dist = radianToDegree(dist);
		dist = dist * 60 * 1.1515;
		
		if (unit.equals("K"))
		{
		    dist = dist * 1.609344;
		}
		else if (unit.equals("N"))
		{
			dist = dist * 0.8684;
		}
		return (dist);
	}
	private double degreeToRadian(double degree)
	{
		return (degree * Math.PI / 180.0);
	}
	private double radianToDegree(double radian)
	{
		  return (radian * 180.0 / Math.PI);
	}
}
