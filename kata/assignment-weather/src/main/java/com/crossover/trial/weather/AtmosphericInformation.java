package com.crossover.trial.weather;

/**
 * encapsulates sensor information for a particular location
 */
class AtmosphericInformation {

    /** temperature in degrees celsius */
    private DataPoint temperature;

    /** wind speed in km/h */
    private DataPoint wind;

    /** humidity in percent */
    private DataPoint humidity;

    /** precipitation in cm */
    private DataPoint precipitation;

    /** pressure in mmHg */
    private DataPoint pressure;

    /** cloud cover percent from 0 - 100 (integer) */
    private DataPoint cloudCover;

    /** the last time this data was updated, in milliseconds since UTC epoch */
    private long lastUpdateTime;

    public AtmosphericInformation() {

    }

    protected AtmosphericInformation(DataPoint temperature, DataPoint wind, DataPoint humidity, DataPoint percipitation, DataPoint pressure, DataPoint cloudCover) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.precipitation = percipitation;
        this.pressure = pressure;
        this.cloudCover = cloudCover;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public DataPoint getTemperature() {
        return temperature;
    }
    public void setTemperature(DataPoint temperature) {
        this.temperature = temperature;
    }
    public DataPoint getWind() {
        return wind;
    }
    public void setWind(DataPoint wind) {
        this.wind = wind;
    }
    public DataPoint getHumidity() {
        return humidity;
    }
    public void setHumidity(DataPoint humidity) {
        this.humidity = humidity;
    }
    public DataPoint getPrecipitation() {
        return precipitation;
    }
    public void setPrecipitation(DataPoint precipitation) {
        this.precipitation = precipitation;
    }
    public DataPoint getPressure() {
        return pressure;
    }
    public void setPressure(DataPoint pressure) {
        this.pressure = pressure;
    }
    public DataPoint getCloudCover() {
        return cloudCover;
    }
    public void setCloudCover(DataPoint cloudCover) {
        this.cloudCover = cloudCover;
    }
    protected long getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    protected void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
