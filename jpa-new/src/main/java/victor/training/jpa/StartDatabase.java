package victor.training.jpa;

import org.hsqldb.server.Server;

public class StartDatabase {

	public static void main(String[] args) {
		Server.main(new String[]{"--database.0", "mem:test","--dbname.0", "test"});
	}
}
