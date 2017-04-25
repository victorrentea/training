package tddmicroexercises.telemetrysystem;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TelemetryDiagnosticControlsTest {

	@Mock
	private TelemetryClient client;
	
	@InjectMocks
	private TelemetryDiagnosticControls controls;
	
	@Before
	public void setupCommonExpectations() {
		when(client.getOnlineStatus()).thenReturn(true);
	}
	
	@Test
	public void disconnects() throws Exception {
		controls.checkTransmission();
		verify(client).disconnect();
	}
	
	
	@Test
	public void sends() throws Exception {
		controls.checkTransmission();
		verify(client).send(TelemetryClient.DIAGNOSTIC_MESSAGE);
	}
	
	@Test
	public void returnsWhatClientReceives() throws Exception {
		when(client.receive()).thenReturn("msg");
		controls.checkTransmission();
		assertEquals("msg", controls.getDiagnosticInfo());
	}
	
	@Test(expected = Exception.class)
	public void throwsWhenNotOnline() throws Exception {
		when(client.getOnlineStatus()).thenReturn(false);
		controls.checkTransmission();
	}
	
	
	@Test
	public void triesToConnectIfNotOnline() throws Exception {
		when(client.getOnlineStatus()).thenReturn(false, true);
		controls.checkTransmission();
		verify(client).connect(TelemetryDiagnosticControls.DIAGNOSTIC_CHANNEL_CONNECTION_STRING);
	}
	
	@Test
	public void doesntRetryMoreThan3Times() throws Exception {
		when(client.getOnlineStatus()).thenReturn(false);
		try {
			controls.checkTransmission();
		} catch (Exception e) {
			// hic !
		}
		verify(client, times(3)).connect(TelemetryDiagnosticControls.DIAGNOSTIC_CHANNEL_CONNECTION_STRING);
	}
	
	

}
