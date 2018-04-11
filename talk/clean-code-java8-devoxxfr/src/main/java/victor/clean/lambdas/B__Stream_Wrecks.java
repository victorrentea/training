package victor.clean.lambdas;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import lombok.Data;








//VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
@Data
class Order {
	private List<OrderLine> orderLines;
	private LocalDate creationDate;
}

@Data
class OrderLine {
	private Product product;
	private int itemCount;
}

@Data
class Product {
	private Long id;
	private boolean deleted;
}

interface ProductRepo {
	List<Long> getExcludedProductIds();
}
