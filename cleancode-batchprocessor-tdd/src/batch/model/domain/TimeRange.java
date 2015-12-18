package batch.model.domain;

import batch.util.DateUtils;


public class TimeRange extends ValueRange<String> {

	private String min;

	private String max;

	protected TimeRange() {
	}

	public TimeRange(String min, String max) {
		validateTimeFormat(min);
		validateTimeFormat(max);
		validateMaxLargerThanMin(min, max);
		this.min = min;
		this.max = max;
	}

	@Override
	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		validateTimeFormat(min);
		this.min = min;
	}

	@Override
	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		validateTimeFormat(max);
		this.max = max;
	}

	public boolean isOverlapping(TimeRange other) {
		if (getMin().compareTo(other.getMin()) < 0) {
			return getMax().compareTo(other.getMin()) > 0;
		} else if (getMin().compareTo(other.getMin()) > 0) {
			return other.getMax().compareTo(getMin()) > 0;
		} else {
			return true;
		}
	}

	public boolean isAfter(String time) {
		validateTimeFormat(time);
		return !(getMin().compareTo(time) < 0);
	}

	@Override
	public String getFormattedRange() {
		return getMin() + " - " + getMax();
	}

	private void validateTimeFormat(String time) {
	    if(time != null) {
    		if (!DateUtils.isTimeValid(time, DateUtils.HOUR_MINUTE_FORMAT)) {
    			throw new IllegalArgumentException("time " + time + " not formatted correctly");
    		}
	    }
	}

	private void validateMaxLargerThanMin(String min, String max) {
		if (!(min.compareTo(max) < 0)) {
			throw new IllegalArgumentException(max + " must be larger than " + min);
		}
	}
}
