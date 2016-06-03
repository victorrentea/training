package victor.perf.interning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class ObviousJsonReader {
	
	public static void main(String[] args) throws Exception {
		List<JsonObject> jsonObjectList = new ArrayList<>();
		System.out.println("Loading huge JSON...");
		List<Map<String, String>> mapList = readJsonAsMap();

		System.out.println("Memory loaded. You have 60 seconds... :)");
		Thread.sleep(1000 * 60);
		
		System.out.println(jsonObjectList.size());
		System.out.println(mapList.size());
		System.out.println("Stopped");
	}

	private static List<Map<String, String>> readJsonAsMap() throws FileNotFoundException {
		List<Map<String, String>> mapList = new ArrayList<>();
		
		try (JsonReader jsonReader = Json.createReader(new BufferedReader(new FileReader("big.json")))) {
			// memory leak happens if reading & testing from the same methods
			JsonArray array = jsonReader.readArray();
			for (JsonValue jsonValue : array) {
				JsonObject object= (JsonObject) jsonValue;
				Map<String, String> map = new HashMap<>();
				for (String key: object.keySet()) {
					map.put(key.intern(), object.getString(key));
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
}
