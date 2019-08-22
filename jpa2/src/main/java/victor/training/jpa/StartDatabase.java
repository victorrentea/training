package victor.training.jpa;

import java.sql.SQLException;

public class StartDatabase {
	public static void main(String[] args) throws SQLException {
		System.out.println("Check out folder: ~/source/db/database/db");
		System.out.println("Started DB...");
		//hsqldb does not support Nested Transactions (REQUIRES_NEW).
//		org.hsqldb.server.Server.main("--database.0 mem:test --dbname.0 test".split(" "));

		// H2 does :)
		org.h2.tools.Server.createTcpServer().start();

	}
}
