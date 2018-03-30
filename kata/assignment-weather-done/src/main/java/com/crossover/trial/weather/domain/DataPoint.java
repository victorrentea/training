package com.crossover.trial.weather.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** A collected point, including some information about the range of collected values */
public class DataPoint {


    /** 1st quartile -- lower bound */
    private int first; 

    /** 2nd quartile -- median value */
    private int second; 

    /** 3rd quartile value -- less noisy upper value */
    private int third; 

    private double mean; 

    /** the total number of measurements */
    private int count; 

    
    private DataPoint() {
    }
    
    protected DataPoint(int first, int second, int third, int mean, int count) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.mean = mean;
        this.count = count;
    }
    
    public double getMean() {
        return mean;
    }
    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
    
    public int getThird() {
        return third;
    }
    
    public int getCount() {
        return count;
    }
    @Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
    
    @Override
	public boolean equals(Object other) {
    	if (other == null) {
    		return false;
    	}
        return this.toString().equals(other.toString());
    }

    public static class Builder {
    	private DataPoint constructed = new DataPoint();
    	
        public Builder withFirst(int first) {
        	constructed.first = first;
            return this;
        }

        public Builder withSecond(int second) {
        	constructed.second = second;
            return this;
        }

        public Builder withMean(int mean) {
            constructed.mean = mean;
            return this;
        }

        public Builder withCount(int count) {
            constructed.count = count;
            return this;
        }

        public Builder withThird(int third) {
            constructed.third = third;
            return this;
        }

        public DataPoint build() {
            DataPoint result = constructed;
            constructed = null;
			return result;
        }
    }
}
