package victor.training.oo.creational.singleton;

import static victor.training.oo.Helper.workSomeTime;

import java.io.IOException;
import java.util.Properties;

public class AppConfiguration {
	private static AppConfiguration INSTANCE; // SOLUTION

	private AppConfiguration() { // SOLUTION
//	public AppConfiguration() { // INITIAL	
		System.out.println("Creating singleton instance");
		properties = readConfiguration();
	}

	public static AppConfiguration getInstance() { // SOLUTION(
		if (INSTANCE == null) {
			INSTANCE = new AppConfiguration();
		}
		return INSTANCE;
	} // SOLUTION)

	private final Properties properties;

	private Properties readConfiguration() {
		System.out.println("Fetching encrypted configuration over HTTPS from Thailand");
		workSomeTime();
		System.out.println("Decrypting");
		workSomeTime();
		Properties props = new Properties();
		try {
			props.load(AppConfiguration.class.getResourceAsStream("dummy.properties"));
			System.out.println("Done");
			return props;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

}
