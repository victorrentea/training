package com.crossover.trial.weather.service;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.crossover.trial.weather.WeatherException;
import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.DataPoint;
import com.crossover.trial.weather.domain.DataPointType;
import com.crossover.trial.weather.domain.Weather;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
	private static final double EPSILON = .0001; 
	
	@Mock
	private WeatherRepository repository;
	
	@InjectMocks
	private WeatherService target = new WeatherService();
	
	private Airport airport = new Airport("code1", 0, 0);
	
	@Before
	public void setupMocks() {
		when(repository.getAirport("code1")).thenReturn(airport);
		when(repository.getAllAirports()).thenReturn(singletonList(airport));
	}
	
	@Test(expected = WeatherException.class)
	public void setInvalidDataPointThrowsError() {
		assertNull(airport.getWeather().getPrecipitation());
		target.setDataPoint("code1", DataPointType.PRECIPITATION, new DataPoint.Builder().withMean(400).build());
	}

	@Test
	public void setValidDataPointSavesAndUpdateLastUpdateTime() {
		assertNull(airport.getWeather().getPrecipitation());
		assertEquals(0, airport.getWeather().getLastUpdateTime());
		target.setDataPoint("code1", DataPointType.PRECIPITATION, new DataPoint.Builder().withMean(50).build());
		assertNotNull(airport.getWeather().getPrecipitation());
		assertEquals(50, airport.getWeather().getPrecipitation().getMean(), EPSILON);
		assertNotNull(airport.getWeather().getLastUpdateTime());
	}
	
	@Test
	public void loadDefaultAirportsDoesNotCrash() throws IOException {
		target.loadDefaultAirports();
	}
	
	@Test
	public void getAllIataCodesReturnsOk() {
		assertEquals(1, target.getAllIataCodes().size());
		assertEquals("code1", target.getAllIataCodes().iterator().next());
	}

	@Test(expected = WeatherException.class)
	public void searchWithNegativeRadiusFails() {
		target.searchAirportWeather("code1", -1);
	}
	
	@Test
	public void searchWithRadius0AlwaysReturnsThatAirportAWeather() {
		assertEquals(1, target.searchAirportWeather("code1", 0).size());
		assertEquals(airport.getWeather(), target.searchAirportWeather("code1", 0).get(0));
	}
	
	@Test
	public void searchWithNon0RadiusReturnsWeatherWithData() {
		assertEquals(0, target.searchAirportWeather("code1", .01).size());
	}

	@Test(expected = WeatherException.class)
	public void searchWithNonNegativeRadiusForUnknownIataThrowsError() {
		when(repository.getAirport("code2")).thenThrow(new WeatherException(""));
		target.searchAirportWeather("code2", .01);
		
	}
	
	@Test
	public void searchWithRadiusReturnsMultipleAirports() {
		Airport airport2 = new Airport("code2", 0.01, 0.01);
		airport.getWeather().setPrecipitation(new DataPoint.Builder().withMean(50).build());;
		airport.getWeather().setLastUpdateTime(System.currentTimeMillis());
		airport2.getWeather().setPrecipitation(new DataPoint.Builder().withMean(50).build());;
		airport2.getWeather().setLastUpdateTime(System.currentTimeMillis());
		when(repository.getAllAirports()).thenReturn(Arrays.asList(
				airport,
				airport2
				));
		List<Weather> list = target.searchAirportWeather("code1", 1000);
		assertEquals(2, list.size());
	}
	
	@Test
	public void searchWithRadiusReturnsOnlyKnownWeatherData() {
		airport.getWeather().setLastUpdateTime(System.currentTimeMillis());
		when(repository.getAllAirports()).thenReturn(Arrays.asList(airport));
		List<Weather> list = target.searchAirportWeather("code1", 1000);
		assertEquals(0, list.size());
	}

	@Test
	public void searchWithRadiusReturnsOldWeatherData() {
		airport.getWeather().setPrecipitation(new DataPoint.Builder().withMean(50).build());;		when(repository.getAllAirports()).thenReturn(Arrays.asList(airport));
		List<Weather> list = target.searchAirportWeather("code1", 1000);
		assertEquals(1, list.size());
	}


	
}
