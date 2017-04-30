package victor.training.jpa;

public class StartDatabase {

	public static void main(String[] args) {
		org.hsqldb.server.Server.main(new String[]{
				"--database.0", "mem:test",
				"--dbname.0", "test"});
	}
}
