package batch.model.domain;

import static org.apache.commons.lang.time.DateUtils.addMonths;
import static org.apache.commons.lang.time.DateUtils.addWeeks;
import static org.apache.commons.lang.time.DateUtils.addYears;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import batch.util.DateUtils;



public class DateRange extends ValueRange<Date> {

	private Date min;

	private Date max;

	protected DateRange() {
	}

	public static DateRange createWeek(Date start) {
		Date end = addWeeks(start, 1);
		return new DateRange(start, end);
	}
	
	public static DateRange createMonthMinusThree(Date start) {
        Date end = addMonths(start, 1);
        // set startdate 3 months in past
        start = addMonths(start, -3);
        return new DateRange(start, end);
    }

	public static DateRange createMonth(Date start) {
		Date end = addMonths(start, 1);
		return new DateRange(start, end);
	}

	public static DateRange createYear(Date start) {
		Date end = addYears(start, 1);
		return new DateRange(start, end);
	}

	public DateRange(Date min, Date max) {
		validateNotNull(min);
		validateMaxLargerThanMin(min, max);
		this.min = DateUtils.truncateTimeStamp(min);
		this.max = DateUtils.truncateTimeStamp(max);
	}

	@Override
	public Date getMin() {
		return (Date) min.clone();
	}

	public void setMin(Date min) {
		validateNotNull(min);
		validateMaxLargerThanMin(min, getMax());
		this.min = DateUtils.truncateTimeStamp(min);
		;
	}

	@Override
	public Date getMax() {
		return max;
	}

	public void setMax(Date max) {
		validateMaxLargerThanMin(getMin(), max);
		this.max = DateUtils.truncateTimeStamp(max);
	}

	@Override
	public int compareTo(ValueRange<Date> other) {
		int result = DateUtils.compareDates(min, other.getMin());
		if (result == 0) {
			result = DateUtils.compareDates(max, other.getMax());
		}
		return result;
	}

	public boolean isOverlapping(DateRange other) {
		if (DateUtils.compareDates(min, other.getMin()) < 0) {
			return DateUtils.compareDates(max, other.getMin()) > 0;
		} else if (DateUtils.compareDates(min, other.getMin()) > 0) {
			return DateUtils.compareDates(other.getMax(), min) > 0;
		} else {
			return true;
		}
	}

	private void validateNotNull(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date must not be null");
		}
	}

	private void validateMaxLargerThanMin(Date min, Date max) {
		if (!(DateUtils.compareDatesWithoutTime(min, max) < 0)) {
			throw new IllegalArgumentException(max + " must be null or larger than " + min);
		}
	}

	@Override
	public String getFormattedRange() {
		return DateUtils.format(getMin()) + " - " + DateUtils.format(getMax());
	}

	@Override
	public boolean contains(Date day) {
		return DateUtils.compareDates(getMin(), day) <= 0 && DateUtils.compareDates(getMax(), day) > 0;
	}

	public List<Date> getCoveredDays() {
		List<Date> result = new ArrayList<Date>();
		if (getMax() != null) {
			for (Date date = getMin(); date.before(getMax()); date = org.apache.commons.lang.time.DateUtils.addDays(date, 1)) {
				result.add(date);
			}
		}
		return result;
	}
}
