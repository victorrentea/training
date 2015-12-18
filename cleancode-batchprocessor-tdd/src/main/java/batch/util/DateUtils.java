package batch.util;

import static org.apache.commons.lang.time.DateUtils.addDays;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.FastDateFormat;

public final class DateUtils {

    private DateUtils(){}
    
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_FORMAT_WITH_WEEKDAY = "EEE dd/MM/yyyy";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String EXACT_TIME_FORMAT = "HH:mm:ss.S";
	public static final String DEFAULT_DATE_TIME_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;
	public static final String HOUR_MINUTE_FORMAT = "HH:mm";
    private static final int MILISECONDS = 1000;
    private static final int SECONDS = 60;
    private static final int MINUTES = 60;
    private static final int HOURS = 24;
    private static final int DAYS = 7;

	/**
	 * Parses the given date String and returns a <code>java.util.Date</code> object
	 * 
	 * @param formattedDate The date according to the format dd/MM/yyyy
	 * @return a <code>java.util.Date</code> object
	 */
	public static Date parseDate(String formattedDate) {
		return parseDate(formattedDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Parses the given date String and returns a <code>java.util.Date</code> object
	 * 
	 * @param formattedDate The date according to the format "dd/MM/yyyy HH:mm:ss"
	 * @return a <code>java.util.Date</code> object
	 */
	public static Date parseDateTime(String formattedDate) {
		return parseDate(formattedDate, DEFAULT_DATE_TIME_FORMAT);
	}

	/**
	 * Parses the given date String and returns a <code>java.util.Date</code> object
	 * 
	 * @param formattedDate The date according to the given format
	 * @param dateFormat The format that the date should adhere to --> from ApplicationResources
	 * @return a <code>java.util.Date</code> object or <code>null</code> if the given <code>formattedDate</code> is <code>null</code>.
	 */
	public static Date parseDate(final String formattedDate, final String dateFormat) {
		if (formattedDate == null || "".equals(formattedDate)) {
			return null;
		}
		try {
			DateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			return formatter.parse(formattedDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Could not parse date " + formattedDate + " according to format " + dateFormat, e);
		}
	}

	/**
	 * Format given date using the {@link #DEFAULT_DATE_FORMAT default} pattern.
	 * 
	 * @param date the date to format
	 * @return the resulting string
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return format(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Format given date using given format pattern.
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use ({@link SimpleDateFormat})
	 * @return the resulting string
	 */
	public static String format(Date date, String pattern) {
		FastDateFormat formatter = FastDateFormat.getInstance(pattern);
		return formatter.format(date);
	}

	/**
	 * Format given date using given format pattern and locale
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use ({@link SimpleDateFormat})
	 * @return the resulting string
	 */
	public static String format(Date date, String pattern, Locale locale) {
		FastDateFormat formatter = FastDateFormat.getInstance(pattern, locale);
		return formatter.format(date);
	}

	public static Date truncateTimeStamp(Date date) {
		if (date == null) {
			return date;
		} else {
			return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		}
	}

	/**
	 * Get the all days in the same week as the given date.
	 * 
	 * @param dateInWeek An arbitrary date
	 * @return
	 */
	public static List<Date> getAllDatesInWeek(Date dateInWeek) {
		List<Date> allWeek = new ArrayList<Date>(7);
		allWeek.add(getDateInWeek(dateInWeek, Calendar.MONDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.TUESDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.WEDNESDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.THURSDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.FRIDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.SATURDAY));
		allWeek.add(getDateInWeek(dateInWeek, Calendar.SUNDAY));
		return allWeek;
	}

	/**
	 * Get the requested day with the given dayOfWeek number in the same week as the given dateInWeek.
	 * 
	 * @param dateInWeek An arbitrary date in the week you want the requested date to be
	 * @param dayOfWeek {@link Calendar#MONDAY}, {@link Calendar#TUESDAY}, {@link Calendar#WEDNESDAY}, {@link Calendar#THURSDAY}, {@link Calendar#FRIDAY},
	 *            {@link Calendar#SATURDAY} or {@link Calendar#SUNDAY}
	 * @return
	 */
	public static Date getDateInWeek(Date dateInWeek, int dayOfWeek) {

		if (dateInWeek == null) {
			throw new IllegalArgumentException("date must not be null");
		}

		if (!Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY)
				.contains(dayOfWeek)) {
			throw new IllegalArgumentException("dayOfWeek is not a valid day");
		}

		Calendar cal = new GregorianCalendar();
		cal.setTime(truncateTimeStamp(dateInWeek));
		cal.add(Calendar.DAY_OF_MONTH, convertDayNumber(dayOfWeek) - convertDayNumber(cal.get(Calendar.DAY_OF_WEEK)));

		return cal.getTime();
	}

	protected static int convertDayNumber(int dayOfWeek) {
		if (Calendar.MONDAY == dayOfWeek) {
			return 1;
		} else if (Calendar.TUESDAY == dayOfWeek) {
			return 2;
		} else if (Calendar.WEDNESDAY == dayOfWeek) {
			return 3;
		} else if (Calendar.THURSDAY == dayOfWeek) {
			return 4;
		} else if (Calendar.FRIDAY == dayOfWeek) {
			return 5;
		} else if (Calendar.SATURDAY == dayOfWeek) {
			return 6;
		} else if (Calendar.SUNDAY == dayOfWeek) {
			return 7;
		} else {
			throw new IllegalArgumentException("dayOfWeek is not a valid day");
		}
	}

	/**
	 * Parses the given date/time String and returns a <code>java.util.Date</code> object
	 * 
	 * @param formattedTime The date according to the format HH:mm:ss
	 * @return a <code>java.util.Date</code> object
	 */
	public static Date parseTime(String formattedTime) {
		return parseDate(formattedTime, DEFAULT_TIME_FORMAT);
	}

	/**
	 * Parses the given hour/minute String and returns a <code>java.util.Date</code> object
	 * 
	 * @param formattedTime The date according to the format HH:mm
	 * @return a <code>java.util.Date</code> object
	 */
	public static Date parseHourMinute(String formattedTime) {
		return parseDate(formattedTime, HOUR_MINUTE_FORMAT);
	}
	
	public static String parseMinutesToHourMinuteString(int minutes) {
	    int hours = minutes/60;     
	    int min = minutes%60;  
	    
	    return String.format("%02d:%02d", hours, min);
    }
	
	public static int parseHourMinuteStringToMinutes(String timestampStr) {
	    String[] tokens = timestampStr.split(":");
	    int hours = 0;
	    int minutes = 0;
	    
	    try {
	        hours = Integer.parseInt(tokens[0]);
	        try {
	            minutes = Integer.parseInt(tokens[1]);
	        } catch (ArrayIndexOutOfBoundsException e) {
	            minutes = 0;
	         }
        } catch (ArrayIndexOutOfBoundsException e) {
           hours = 0;
        }
	    
	    int duration = 60 * hours +  minutes ;
        return duration;
    }

	/**
	 * Validate a Time (Hour + Minutes)
	 * 
	 * @param argTime String
	 * @return boolean
	 */
	public static boolean isTimeValid(String argTime, String format) {
		DateFormat timeFormat = new SimpleDateFormat(format);
		timeFormat.setLenient(false);
		try {
			timeFormat.parse(argTime);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static Date getCurrentTime() {
		return new Date();
	}

	public static Date getCurrentDateWithoutTime() {
		return truncateTimeStamp(getCurrentTime());
	}

	public static Date getTomorrow() {
		return addDays(getCurrentDateWithoutTime(), 1);
	}

	public static int compareDates(Date first, Date second) {
		if (first == null) {
			return second == null ? 0 : 1;
		} else if (second == null) {
			return first == null ? 0 : -1;
		} else {
			return first.compareTo(second);
		}
	}

	public static int compareDatesWithoutTime(Date first, Date second) {
		return compareDates(truncateTimeStamp(first), truncateTimeStamp(second));
	}

	public static Date min(Date first, Date second) {
		if (compareDates(first, second) <= 0) {
			return first;
		} else {
			return second;
		}
	}

	public static Date max(Date first, Date second) {
		if (compareDates(first, second) <= 0) {
			return second;
		} else {
			return first;
		}
	}

	public static Date toDate(Calendar cal) {
		return cal == null ? null : cal.getTime();
	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = null;
		if (date != null) {
			cal = new GregorianCalendar();
			cal.setTimeZone(TimeZone.getTimeZone("CET"));
			cal.setTimeInMillis(date.getTime());
		}
		return cal;
	}

	public static XMLGregorianCalendar extractXmlDateTime(Date timestamp) {
		if (timestamp == null) {
			return null;
		}
		XMLGregorianCalendar date = null;
		try {
			GregorianCalendar startDate = new GregorianCalendar();
			startDate.setTime(timestamp);
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
			date.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			date.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static XMLGregorianCalendar extractXmlDate(Date timestamp) {
		if (timestamp == null) {
			return null;
		}
		XMLGregorianCalendar date = null;
		try {
			GregorianCalendar startDate = new GregorianCalendar();
			startDate.setTime(timestamp);
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
			date.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			date.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
			date.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static XMLGregorianCalendar extractXmlTime(Date timestamp) {
		if (timestamp == null) {
			return null;
		}
		XMLGregorianCalendar time = null;
		try {
			GregorianCalendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(timestamp);
			time = DatatypeFactory.newInstance().newXMLGregorianCalendar(endCalendar);
			time.setDay(DatatypeConstants.FIELD_UNDEFINED);
			time.setMonth(DatatypeConstants.FIELD_UNDEFINED);
			time.setYear(DatatypeConstants.FIELD_UNDEFINED);
			time.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			time.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return time;
	}

	public static XMLGregorianCalendar extractXmlTime(String timestampString) {
		Date timestamp = parseHourMinute(timestampString);
		return extractXmlTime(timestamp);
	}
	
	public static Duration extractXmlDuration(long durationInMs) {
	    try {
            return DatatypeFactory.newInstance().newDuration(durationInMs);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
	}

	public static String extractHourMinute(final XMLGregorianCalendar date) {
		GregorianCalendar calendar = date.toGregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone("CET"));
		Date date2 = calendar.getTime();
		
		return format(date2, HOUR_MINUTE_FORMAT);
	}

	public static Date extractDate(final XMLGregorianCalendar date) {
		return date.toGregorianCalendar().getTime();
	}

	public static Date extractDate(final Calendar date) {
		return date.getTime();
	}

	public static String format(XMLGregorianCalendar xmlGregorianCalendar, String pattern) {
		return format(xmlGregorianCalendar.toGregorianCalendar().getTime(), pattern);
	}

	/**
	 * @param target
	 * @param first
	 * @param second
	 * @return True if the given target date is in between the first date and the second date (inclusive first and second date)
	 */
	public static boolean inBetween(Date target, Date first, Date second) {
		boolean targetAfterFirst = DateUtils.compareDates(first, target) <= 0;
		boolean targetBeforeSecond = DateUtils.compareDates(target, second) <= 0;
		return (targetAfterFirst && targetBeforeSecond);
	}

	public static Date ceiling(Date first, Date second) {
		if (first == null) {
			return second;
		}
		if (second == null) {
			return first;
		}
		if (first.after(second)) {
			return first;
		} else {
			return second;
		}
	}

	public static XMLGregorianCalendar extractXmlTimeExact(String timestampString) {
		Date timestamp = parseDate(timestampString, EXACT_TIME_FORMAT);
		if (timestamp == null) {
			return null;
		}
		XMLGregorianCalendar time = null;
		try {
			GregorianCalendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(timestamp);
			time = DatatypeFactory.newInstance().newXMLGregorianCalendar(endCalendar);
			time.setDay(DatatypeConstants.FIELD_UNDEFINED);
			time.setMonth(DatatypeConstants.FIELD_UNDEFINED);
			time.setYear(DatatypeConstants.FIELD_UNDEFINED);
			time.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			time.normalize();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return time;
	}

    public static Calendar extractCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        return DateUtils.toCalendar(extractDate(xmlGregorianCalendar));
    }

    /**
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int getNumberOfWeeks(Date fromDate, Date toDate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(fromDate);
        calendar2.setTime(toDate);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        int diffWeeks = (int)diff / (DAYS * HOURS * MINUTES * SECONDS * MILISECONDS);
        return diffWeeks;
    }

    public static Date getYesterday() {
        return addDays(getCurrentDateWithoutTime(), -1);
    }
}
