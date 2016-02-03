package com.crossover.trial.weather.domain;

public enum DataPointType {
	WIND(0, Double.MAX_VALUE), 
	TEMPERATURE(-50, 100), 
	HUMIDTY(0, 100), 
	PRESSURE(650, 800), 
	CLOUDCOVER(0, 100), 
	PRECIPITATION(0, 100);

	private double left;
	private double right;

	private DataPointType(double left, double right) {
		this.left = left;
		this.right = right;
	}

	public boolean isValid(double mean) {
		return mean >= left && mean < right;
	}

	@Override
	public String toString() {
		return name() + "[" + left + "," + right + "]";
	}

}
