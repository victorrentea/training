package batch.model.domain;

import java.util.Date;

public class TimeSlot {
    private Date date;
    private TimeRange timeRange;
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
    public TimeRange getTimeRange() {
        return timeRange;
    }
    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

}
