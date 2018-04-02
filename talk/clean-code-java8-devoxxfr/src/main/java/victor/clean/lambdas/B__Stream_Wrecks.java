package victor.clean.lambdas;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;

import lombok.Data;

public class B__Stream_Wrecks {
	
	private ProductRepo productRepo;
	
	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		List<Long> excludedProductIds = productRepo.getExcludedProductIds();
		return orders.stream()
				.filter(o -> o.getCreationDate().isAfter(LocalDate.now().minusYears(1)))
				.flatMap(o -> o.getOrderLines().stream())
				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)))
				.entrySet()
				.stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.filter(p -> !p.isDeleted())
				.filter(p->excludedProductIds.contains(p.getId()))
				.collect(toList());
	}
}


// -- dummy data model --
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
