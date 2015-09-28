package victor.training.spring.advanced;

import java.io.File;

import org.springframework.beans.factory.FactoryBean;

public class MyStringFactoryBean implements FactoryBean<String> {
	
	private File file;

	@Override
	public String getObject() throws Exception {
		return "String intors din fisier : " + file.getAbsolutePath();
	}

	@Override
	public Class<?> getObjectType() {
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public final File getFile() {
		return file;
	}

	public final void setFile(File file) {
		this.file = file;
	}



}
