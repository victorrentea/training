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



class ProductService {
	
	private ProductRepo productRepo;
	
	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		List<Long> excludedIds = productRepo.getExcludedProductIds();
		Map<Product, Integer> productCount = getProductCount(orders);
		Predicate<Product> productIsNotExcluded = p-> !excludedIds.contains(p.getId());
		return productCount.entrySet().stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.filter(Product::isNotDeleted)
				.filter(productIsNotExcluded)
				.collect(toList());
	}

	private Map<Product, Integer> getProductCount(List<Order> orders) {
		return orders.stream()
				.filter(this::orderWasPlacedInTheLastYear)
				.flatMap(o -> o.getOrderLines().stream())
				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
	}

	private boolean orderWasPlacedInTheLastYear(Order o) {
		return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
	}
}




//VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
@Data
class Order {
	private Long id;
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
	public boolean isNotDeleted() {
		return !deleted; 
	}
	private Long id;
	private boolean deleted;
}

interface ProductRepo {
	List<Long> getExcludedProductIds();
}
