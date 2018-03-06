package ro.victor.training.jpa.orm.advanced;

public class StartDatabase {
	public static void main(String[] args) {
		org.hsqldb.server.Server.main("--database.0 mem:test --dbname.0 test".split(" "));
	}
}
