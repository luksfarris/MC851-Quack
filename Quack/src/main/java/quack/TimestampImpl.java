package quack;

import java.text.*;
import java.util.*;

public class TimestampImpl implements Timestamp {
	
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss z";
	
	@Override
	public long now() {
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}

	@Override
	public String toString(long timestamp, String timezone) {
		DateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(timezone));
		return formatter.format(new Date(timestamp * 1000));
	}

	@Override
	public long fromString(String timestamp) {
		DateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		Date date;
		try {
			date = formatter.parse(timestamp);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

}
