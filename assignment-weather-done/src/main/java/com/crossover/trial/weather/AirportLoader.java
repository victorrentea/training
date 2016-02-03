package com.crossover.trial.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.domain.Airport;
import com.crossover.trial.weather.domain.DST;

/**
 * A simple airport loader which reads a file from disk and sends entries to the webservice
 *
 * TODO: Implement the Airport Loader
 * 
 * @author code test administrator
 */
public class AirportLoader {

    private final WebTarget collect;

    public AirportLoader(String baseEndpoint) {
        Client client = ClientBuilder.newClient(); //since it's intended to be used in a standalone app, no risk of resource leak due to not calling .close on this client
        collect = client.target(baseEndpoint + "/collect");
    }
    

    public void upload(InputStream airportDataStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
        String line = null;
        
        
        List<Airport> airports = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
        	List<String> tokens = tokenize(line);
			double longitude = Double.parseDouble(tokens.get(7));
			double latitude = Double.parseDouble(tokens.get(6));
			
			Airport airport = new Airport(tokens.get(4), latitude, longitude);
			airport.setName(tokens.get(1));
			
			Integer altitude = tokens.get(8).isEmpty()?null:Integer.parseInt(tokens.get(8));
			airport.setAltitudeFt(altitude);
			
			airport.setCity(tokens.get(2));
			
			airport.setCountry(tokens.get(3));

			DST dst = DST.valueOf(tokens.get(10));
			airport.setDst(dst);
			
			airport.setIcao(tokens.get(5));
			
			TimeZone timeZone = tokens.get(9).isEmpty()?null:TimeZone.getTimeZone("GMT"+tokens.get(9));
			airport.setTimeZone(timeZone);
        	
			airports.add(airport);
        }
        
        for (Airport airport : airports) {
        	Response response = collect.path("/airport").request().post(Entity.entity(airport, MediaType.APPLICATION_JSON));
        	System.out.println("Uploaded airport "+airport.getIata()+": " + (response.getStatus()==200?"OK":"FAILED"));
        }
	}
    

	public List<String> tokenize(String line) {
		List<String> list = new ArrayList<>();
		String[] parts = line.split("\\s*,\\s*");
		for (int i = 0; i < parts.length; i++) {
			String item = parts[i];
			if (item.length() > 0 && item.charAt(0) == '"' && item.charAt(item.length() - 1) != '"') {
				i++;
				while (i < parts.length && parts[i].charAt(parts[i].length() - 1) != '"') {
					item += "," + parts[i];
					i++;
				}
				if (i == parts.length) {
					throw new WeatherException("Non-closed double quote !");
				}
				item+="," +parts[i];
			}
			if (item.startsWith("\"") && item.endsWith("\"")) {
				item = item.substring(1, item.length()-1);
			}
			list.add(item);
		}
		return list;
	}

    public static void main(String args[]) throws IOException{
        File airportDataFile = new File(args[0]);
        if (!airportDataFile.exists() || airportDataFile.length() == 0) {
            System.err.println(airportDataFile + " is not a valid input");
            System.exit(1);
        }

        AirportLoader al = new AirportLoader("http://localhost:8080");
        try (FileInputStream fis = new FileInputStream(airportDataFile)) {
        	al.upload(fis);
        }
    }
}
