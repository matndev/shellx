package app.shellx.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public final class DateTimeManager {
	
	public static ZonedDateTime convertDateToZonedDateTime(Date dateToConvert) {
//	    return Instant.ofEpochMilli(dateToConvert.getTime())
//	      .atZone(ZoneId.systemDefault())
//	      .toLocalDateTime();
		return ZonedDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}
}
