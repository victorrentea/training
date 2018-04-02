package victor.clean.lambdas;

import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepo extends JpaRepository<Order, Long> { // J'aime Spring Data!
	Stream<Order> findActiveTrue();
}

class OrderExporter {
	private OrderRepo repo;
	
	private String toExportString(Order order) {
		return "something\n";
	}
	
	public void exportToFile(Writer writer) {
		repo.findActiveTrue()
			.map(this::toExportString)
			.forEach(writer::write);
	}
	
	

}