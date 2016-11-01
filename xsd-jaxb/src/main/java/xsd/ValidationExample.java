package xsd;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

public class ValidationExample {
	public static void main(String[] args) {
		InputStream xmlStream = ValidationExample.class.getResourceAsStream("/xsd/employee-instance.xml");
		validate("employee.xsd", xmlStream);
	}

	private static void validate(final String schemaName, InputStream xmlStream) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

		// associate the schema factory with the resource resolver, which is
		// responsible for resolving the imported XSD's
		schemaFactory.setResourceResolver(new ResourceResolver());

		InputStream is = openSchema(schemaName);
		try {
			Schema schema = schemaFactory.newSchema(new StreamSource(is));
			Validator validator = schema.newValidator();

			validator.validate(new StreamSource(xmlStream));
			System.out.println("XML is valid !");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private static InputStream openSchema(String xsdFileName) {
		return ValidationExample.class.getResourceAsStream("/xsd/" + xsdFileName);
	}

	public static class ResourceResolver implements LSResourceResolver {

		public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
				String baseURI) {

			InputStream resourceAsStream = openSchema(systemId);
			return new Input(publicId, systemId, resourceAsStream);
		}
	}

	public static class Input implements LSInput {

		private String publicId;

		private String systemId;

		public String getPublicId() {
			return publicId;
		}

		public void setPublicId(String publicId) {
			this.publicId = publicId;
		}

		public String getBaseURI() {
			return null;
		}

		public InputStream getByteStream() {
			return null;
		}

		public boolean getCertifiedText() {
			return false;
		}

		public Reader getCharacterStream() {
			return null;
		}

		public String getEncoding() {
			return null;
		}

		public String getStringData() {
			synchronized (inputStream) {
				try {
					byte[] input = new byte[inputStream.available()];
					inputStream.read(input);
					String contents = new String(input);
					return contents;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Exception " + e);
					return null;
				}
			}
		}

		public void setBaseURI(String baseURI) {
		}

		public void setByteStream(InputStream byteStream) {
		}

		public void setCertifiedText(boolean certifiedText) {
		}

		public void setCharacterStream(Reader characterStream) {
		}

		public void setEncoding(String encoding) {
		}

		public void setStringData(String stringData) {
		}

		public String getSystemId() {
			return systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public BufferedInputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(BufferedInputStream inputStream) {
			this.inputStream = inputStream;
		}

		private BufferedInputStream inputStream;

		public Input(String publicId, String sysId, InputStream input) {
			this.publicId = publicId;
			this.systemId = sysId;
			this.inputStream = new BufferedInputStream(input);
		}
	}
}
