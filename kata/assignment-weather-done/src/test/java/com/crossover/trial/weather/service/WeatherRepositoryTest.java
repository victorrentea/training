package com.crossover.trial.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.crossover.trial.weather.WeatherException;
import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.Weather;

public class WeatherRepositoryTest {

	private WeatherRepository target = new WeatherRepository();
	
	private Airport dummyAirport = new Airport("code1", 0,0); 
	
	@Before
	public void insertDummyAirport() {
		target.saveAirport(dummyAirport);
	}
	
	@Test
	public void findInexistentReturnsNull() {
		assertNull(target.findAirport("code2"));
	}

	@Test(expected=WeatherException.class)
	public void findInexistentThrowsError() {
		target.getAirport("code2");
	}
	
	@Test
	public void addDuplicateOverwritesAirportDataAndPreservesWeatherInfo() {
		Weather weather = new Weather();
		dummyAirport.setWeather(weather);
		target.saveAirport(new Airport("code1", 0,0));
		assertEquals(weather, target.getAirport("code1").getWeather());
	}
	
	@Test
	public void getAllAirportsOk() {
		target.saveAirport(new Airport("code2", 0,0));
		assertEquals(2, target.getAllAirports().size());
	}
	
	@Test
	public void removeAirportOk() {
		assertNotNull(target.findAirport("code1"));
		target.removeAirport("code1");
		assertNull(target.findAirport("code1"));
	}
	
	@Test(expected=WeatherException.class)
	public void removeUnexistendAirportFails() {
		target.removeAirport("code1");
		target.removeAirport("code1");
	}

}
