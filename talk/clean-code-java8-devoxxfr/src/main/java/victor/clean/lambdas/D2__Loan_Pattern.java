package victor.clean.lambdas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepo extends JpaRepository<Order, Long> { // J'aime Spring Data!
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
class FileExporter {
	private final static Logger log = LoggerFactory.getLogger(FileExporter.class);
			
	public File exportFile(String fileName, Consumer<Writer> contentWriter) throws Exception {
		log.debug("Start");
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			contentWriter.accept(writer);
			log.debug("Done");
			return file;
		} catch (Exception e) {
			// TODO send email notification
			log.debug("Coucou!", e); // TERREUR !
			throw e;
		}
	}
	public static void main(String[] args) throws Exception {
		FileExporter fileExporter = new FileExporter();
		OrderExportWriter orderExportWriter = new OrderExportWriter();
		UserExporterWriter userExporterWriter = new UserExporterWriter();
		
		fileExporter.exportFile("orders.txt", Unchecked.consumer(orderExportWriter::writeContent));
		fileExporter.exportFile("users.txt", Unchecked.consumer(userExporterWriter::writeUsers));
	}

}
class OrderExportWriter {
	private OrderRepo repo;
	protected void writeContent(Writer writer) throws IOException {
		writer.write("OrderID;Date\n");
		try (Stream<Order> findByActiveTrue = repo.findByActiveTrue()) {
			findByActiveTrue
				.map(o -> o.getId() + ";" + o.getCreationDate())
				.forEach(Unchecked.consumer(writer::write));
		}
	}
}
class UserExporterWriter  {
	protected void writeUsers(Writer writer) throws IOException {
		writer.write("UserId;Usenrname...\n");
//		repo.findByActiveTrue()
//			.map(o -> o.getId() + ";" + o.getCreationDate())
//			.forEach(Unchecked.consumer(writer::write));
	}
}