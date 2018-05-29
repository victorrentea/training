package victor.keys.logger.utils;

import java.io.File;

import org.apache.commons.io.IOUtils;

import lombok.SneakyThrows;

public class GetCurrentProcessTitle {
	@SneakyThrows
	public static String getCurrentProcessTitle() {
		String getControlIdScriptPath = new File("getControlId.ahk").getAbsolutePath();
		Process process = Runtime.getRuntime().exec(new String[]{"autohotkey.exe", getControlIdScriptPath});
		String controlDetails = IOUtils.toString(process.getInputStream(), "UTF-8");
		String controlHWND = controlDetails.split("\\s")[3];
		String windowTitle = controlDetails.substring(controlDetails.indexOf(" || "));
		System.out.println("Control Details: " + controlDetails);
		return windowTitle;
	}
}
