package ro.victor.unittest.mocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class WhyToCapture {

	static class Audit {
		private String code;
		public Audit(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
	static class AuditPD {
		public void insertAudit(Audit audit, String stuff) {
			//face chestii in baza.. nu vreay sa intru aici cu testu mei
		}

		public void faAltceva() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static void metodaProductie(AuditPD auditPD) {
		auditPD.insertAudit(new Audit("ChestiDeTestat"), "stuff");
		
		auditPD.faAltceva();
	}
	
	@Test
	public void testPentruMetodaProductie() {
		// Arrange
		AuditPD auditPDMock = mock(AuditPD.class);
		
		// Act
		metodaProductie(auditPDMock);
		
		// Assert
		ArgumentCaptor<Audit> auditCaptor = ArgumentCaptor.forClass(Audit.class);
		ArgumentCaptor<String> stuffCaptor = ArgumentCaptor.forClass(String.class);
		
		
		verify(auditPDMock, times(1)).insertAudit(auditCaptor.capture(), stuffCaptor.capture());
		
		Audit audit = auditCaptor.getValue();
		assertEquals("ChestiDeTestat", audit.getCode());
		assertEquals("stuff", stuffCaptor.getValue());
		
		verifyNoMoreInteractions(auditPDMock); //nici o alta metoda nu s-a invocat pe acest mock
		
	}
	
	
}
