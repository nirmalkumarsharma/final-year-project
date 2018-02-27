package org.g23.json.calc;

import java.sql.Timestamp;

public class CalculateTimeDifference
{
	public static long compareTwoTimeStamps(Timestamp timestamp1, Timestamp timestamp2)
	{
	    long milliseconds1 = timestamp1.getTime();
	    long milliseconds2 = timestamp2.getTime();

	    long diff = milliseconds2 - milliseconds1;
	    diff=Math.abs(diff);
	    //long diffSeconds = diff / 1000;
	    long diffMinutes = diff / (60 * 1000);
	    //long diffHours = diff / (60 * 60 * 1000);
	    //long diffDays = diff / (24 * 60 * 60 * 1000);

	    return diffMinutes;
	}
}
