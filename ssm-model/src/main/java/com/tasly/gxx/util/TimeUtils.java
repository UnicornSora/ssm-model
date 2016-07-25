package com.tasly.gxx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 * 
 */
public class TimeUtils {
	private final static long minute = 60 * 1000;// 1����
	private final static long hour = 60 * minute;// 1Сʱ
	private final static long day = 24 * hour;// 1��
	private final static long month = 31 * day;// ��
	private final static long year = 12 * month;// ��


	public static final SimpleDateFormat DATE_FORMAT_DATE_D = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_DATE_M = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_DATE_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private TimeUtils() {
		throw new AssertionError();
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DATE_FORMAT_DATE_S);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	
	public static String getTimeFormatText(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "��ǰ";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "����ǰ";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "��ǰ";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "Сʱǰ";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "����ǰ";
		}
		return "�ո�";
	}
}
