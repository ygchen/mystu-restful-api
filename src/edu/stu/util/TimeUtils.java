package edu.stu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class TimeUtils {
	
	public static long startOf(long date)
	{
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(date);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);

		return cal.getTimeInMillis();
	}
	
	public static final String formatTime(long time2format, String pattern, String sameDatePattern) {
        Calendar thenCal =new GregorianCalendar();
        thenCal.setTimeInMillis(time2format);
        Date thenDate = thenCal.getTime();
        Calendar nowCal = new GregorianCalendar();
        nowCal.setTimeInMillis(System.currentTimeMillis());

        java.text.DateFormat f;

        //是否同一天
        if (thenCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)
                && thenCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)
                && thenCal.get(Calendar.DAY_OF_MONTH) == nowCal.get(Calendar.DAY_OF_MONTH)) {
            f = new SimpleDateFormat(sameDatePattern);
        } else {
            f = new SimpleDateFormat(pattern);
        }
        return f.format(thenDate);
    }
	
	public static final String format(long time, String pattern) {
		return new SimpleDateFormat(pattern).format(time);
	}
	
	public static final String format(Date date, String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static void main(String[] args) throws ParseException
	{
		System.err.println(format(1349107200000L,"yyyy-M-d HH:mm "));
		System.err.println(new SimpleDateFormat("yyyy-M-d HH:mm").parse("2012-10-1 10:00").getTime());
	}
}
