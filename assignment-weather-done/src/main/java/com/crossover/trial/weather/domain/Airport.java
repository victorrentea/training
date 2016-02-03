package com.crossover.trial.weather.domain;

import java.util.TimeZone;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Airport {

    /** the three letter IATA code */
    private String iata; 

    /** latitude value in degrees */
    private double latitude; 

    /** longitude value in degrees */
    private double longitude; 
    
    private String name;
    private String city;
    private String country;
    private String icao;
    private Integer altitudeFt;
    private TimeZone timeZone;
    private DST dst;
    
    
    @JsonIgnore
    private Weather weather = new Weather();

    // protected so that Jersey can unmarshall it 
    protected Airport() {
	}
    
    public Airport(String iata, double latitude, double longitude) {
		this.iata = iata;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	public final String getCity() {
		return city;
	}

	public final void setCity(String city) {
		this.city = city;
	}

	public final String getCountry() {
		return country;
	}

	public final void setCountry(String country) {
		this.country = country;
	}

	public final String getIcao() {
		return icao;
	}

	public final void setIcao(String icao) {
		this.icao = icao;
	}

	public final Integer getAltitudeFt() {
		return altitudeFt;
	}

	public final void setAltitudeFt(Integer altitudeFt) {
		this.altitudeFt = altitudeFt;
	}

	public final TimeZone getTimeZone() {
		return timeZone;
	}

	public final void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public final DST getDst() {
		return dst;
	}

	public final void setDst(DST dst) {
		this.dst = dst;
	}
	
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Override
	public boolean equals(Object other) {
        if (other instanceof Airport) {
            return ((Airport)other).iata.equals(this.iata);
        }
        return false;
    }
}
