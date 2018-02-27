package org.g23.json.calc;

public class CalculateDistance
{
	private final int convFactor=-7;
	private GPSCoordinateDistance gpsCoordinateDistance;
	
	public double calculateDistance(int long1Int, int lat1Int, int long2Int, int lat2Int, String unit)
	{
		double long1=long1Int*Math.pow(10, convFactor);
		double lat1=lat1Int*Math.pow(10, convFactor);
		
		double long2=long2Int*Math.pow(10, convFactor);
		double lat2=lat2Int*Math.pow(10, convFactor);
		
		gpsCoordinateDistance=new GPSCoordinateDistance();
		
		return gpsCoordinateDistance.gpsDistance(long1, lat1, long2, lat2, unit);
	}
}
