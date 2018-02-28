package org.g23.entities.json;

import java.sql.Timestamp;
import java.util.List;

public class StayPoint
{
	Timestamp meanTime;
    private int latitudeE7;
    private int longitudeE7;
    private int accuracy;
    Timestamp arrivalTime;
    Timestamp departureTime;
    private List<Activity> activity;
	public StayPoint(Timestamp meanTime, int latitudeE7, int longitudeE7, int accuracy, Timestamp arrivalTime,
			Timestamp departureTime, List<Activity> activity) {
		super();
		this.meanTime = meanTime;
		this.latitudeE7 = latitudeE7;
		this.longitudeE7 = longitudeE7;
		this.accuracy = accuracy;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.activity = activity;
	}
	public Timestamp getMeanTime() {
		return meanTime;
	}
	public void setMeanTime(Timestamp meanTime) {
		this.meanTime = meanTime;
	}
	public int getLatitudeE7() {
		return latitudeE7;
	}
	public void setLatitudeE7(int latitudeE7) {
		this.latitudeE7 = latitudeE7;
	}
	public int getLongitudeE7() {
		return longitudeE7;
	}
	public void setLongitudeE7(int longitudeE7) {
		this.longitudeE7 = longitudeE7;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public Timestamp getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Timestamp getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}
	public List<Activity> getActivity() {
		return activity;
	}
	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}
}