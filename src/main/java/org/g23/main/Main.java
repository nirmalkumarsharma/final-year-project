package org.g23.main;

import java.io.IOException;
import java.util.HashSet;

import org.g23.entities.json.out.StayPoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Main
{
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
	{
		StayPointDetection stayPointDetection=new StayPointDetection();
		HashSet<StayPoint> stayPoints = stayPointDetection.detectStayPoint();
		KmlFileGenerator fileGenerator = new KmlFileGenerator();
		fileGenerator.generateKML(stayPoints);
	}
}
