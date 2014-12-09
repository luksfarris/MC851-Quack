package quack;

import java.text.*;
import java.util.*;

public class TimestampImpl implements Timestamp {
	
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public long now() {
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}

	@Override
	public String toString(long timestamp, String timezone) {
		// Por enquanto, o fuso horário deve ser UTC:
		assert timezone.equals("UTC");
		DateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));
		return formatter.format(new Date(timestamp * 1000)) + " " + timezone;
	}

	@Override
	public long fromString(String timestamp) {
		// Exige que o fuso horário seja UTC por enquanto:
		String timezone = "UTC";
		int tzk = timestamp.indexOf(timezone);
		assert tzk >= 0;
		timestamp = timestamp.substring(0,tzk).trim();
		DateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));
		try {
			Date date = formatter.parse(timestamp);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   	        return 0L;
		}
	}
	
	@Override
	public long fromString(String timestamp, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			Date date = formatter.parse(timestamp);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

}
