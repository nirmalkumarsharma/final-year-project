package org.g22.json.entities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataExtractor
{
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
	{
		File file=new File("/home/nirmal/Downloads/Location_History.json");
		ObjectMapper mapper=new ObjectMapper();
		LocationData data=mapper.readValue(file, LocationData.class);
		String txt=String.valueOf(data.getLocations().get(4).getActivity());
		System.out.println(txt);
	}
}
