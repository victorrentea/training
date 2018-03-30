package com.crossover.trial.properties.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

public class FileSystemConfigLoaderTest {

	private static final File SRC_MAIN_RESOURCES_FOLDER =  new File("src/main/resources").toPath().toFile();
	private static final String FILE_URI =  "file://"+SRC_MAIN_RESOURCES_FOLDER.getAbsolutePath()+"/jdbc.properties";
	
	@Test
	public void acceptsUris() {
		assertTrue(new FileSystemConfigLoader().canLoad(FILE_URI));
	}
	
	@Test
	public void opensUris() {
		Map<String, String> map = new FileSystemConfigLoader().load(FILE_URI);
		assertEquals("true", map.get("JPA_SHOWSQL"));
	}

}
