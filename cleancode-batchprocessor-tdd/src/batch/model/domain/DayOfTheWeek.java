package batch.model.domain;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public enum DayOfTheWeek implements Comparable<DayOfTheWeek> {

	// Note: Ordinal used in comparator below, so don't change the order
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;

	public static final Comparator<DayOfTheWeek> DEFAULT_COMPARATOR = new Comparator<DayOfTheWeek>() {
		@Override
		public int compare(DayOfTheWeek day1, DayOfTheWeek day2) {
			return day1.ordinal() - day2.ordinal();
		}
	};

	public static DayOfTheWeek getDayOfTheWeek(Date day) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(day);
		return getDayOfTheWeek(cal);
	}

	public static DayOfTheWeek getDayOfTheWeek(Calendar day) {
		switch (day.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return DayOfTheWeek.MONDAY;
		case Calendar.TUESDAY:
			return DayOfTheWeek.TUESDAY;
		case Calendar.WEDNESDAY:
			return DayOfTheWeek.WEDNESDAY;
		case Calendar.THURSDAY:
			return DayOfTheWeek.THURSDAY;
		case Calendar.FRIDAY:
			return DayOfTheWeek.FRIDAY;
		case Calendar.SATURDAY:
			return DayOfTheWeek.SATURDAY;
		case Calendar.SUNDAY:
			return DayOfTheWeek.SUNDAY;
		default:
			// Can not happen
			return null;
		}
	}
}
