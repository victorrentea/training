package com.crossover.trial.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.AuditReport;
import com.crossover.trial.weather.domain.DataPoint;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceTest {
	
	private static final double EPSILON = .00001;

	@Mock
	private WeatherRepository repository;
	
	@InjectMocks
	private AuditService service = new AuditService();

	private Airport airport = new Airport("code1", 0, 0);
	
	@Before
	public void setupMocks() {
		when(repository.getAllAirports()).thenReturn(Collections.singletonList(airport));
		airport.getWeather().setLastUpdateTime(System.currentTimeMillis());
		airport.getWeather().setHumidity(new DataPoint.Builder().withMean(20).build());
	}
	
	@Test
	public void noRecords() {
		reset(repository);
		when(repository.getAllAirports()).thenReturn(Collections.emptyList());
		AuditReport report = service.getReport();
		assertEquals(0, report.getDatasize());
		assertTrue(report.getAirportFrequency().isEmpty());
	}
	
	@Test
	public void oneSingleAuditRecord() {
		service.auditWeatherSearch("code1", 1);
		AuditReport report = service.getReport();
		assertEquals(1, report.getDatasize());
		assertEquals(1D, (double)report.getAirportFrequency().get("code1"), EPSILON);
		assertEquals(1, report.getRadiusFrequency()[0]);
	}
	
	@Test
	public void twoAuditRecords() {
		service.auditWeatherSearch("code1", 1);
		service.auditWeatherSearch("code2", 100);
		AuditReport report = service.getReport();
		assertEquals(1, report.getDatasize());
		assertEquals(.5, (double)report.getAirportFrequency().get("code1"), EPSILON);
		assertEquals("One of two radiuses was under 10", 1, report.getRadiusFrequency()[0], EPSILON);
	}
	
	@Test
	public void oldDataPoint() throws ParseException {
		airport.getWeather().setLastUpdateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01").getTime());
		AuditReport report = service.getReport();
		assertEquals(0, report.getDatasize());
	}

	@Test
	public void emptyDataPoint() throws ParseException {
		airport.getWeather().setHumidity(null); // NO DATA
		AuditReport report = service.getReport();
		assertEquals(0, report.getDatasize());
	}
	
	@Test
	public void radiusFrequencyHistogramSizeSpansToMaxRadiusDiv10Plus1() throws ParseException {
		service.auditWeatherSearch("_", 109);
		AuditReport report = service.getReport();
		assertEquals(109/10 + 1, report.getRadiusFrequency().length);
	}
}
