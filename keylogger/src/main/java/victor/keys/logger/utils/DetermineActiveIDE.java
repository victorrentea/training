package victor.keys.logger.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lombok.SneakyThrows;

public class DetermineActiveIDE {

	public enum IDE {
		ECLIPSE, IDEA
	}
	
	public static void main(String[] args) {
		System.out.println(DetermineActiveIDE.getActiveIDE());
	}
	
	public static IDE getActiveIDE() {
		if (lookForProcess("eclipse.exe")) {
			return IDE.ECLIPSE;
		}
		if (lookForProcess("idea")) {
			return IDE.IDEA;
		}
		return null;
	}
	
	@SneakyThrows
	private static boolean lookForProcess(String processName) {
		String line;
		String pidInfo ="";

		Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");

		BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

		while ((line = input.readLine()) != null) {
		    pidInfo+=line; 
		}

		input.close();

		return pidInfo.contains(processName);
	}
}
