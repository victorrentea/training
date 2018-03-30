package victor.clean.lambdas;

import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
public class NameYourLambdas {

	//@Autowired
	private ARepo aRepo;
	
	private List<B> getAllA() {
		List<A> allA = aRepo.findAll();
		return allA.stream()
			.map(a -> {
				B b = new B();
				b.setFirstNameB(a.getFirstNameA());
				b.setLastNameB(a.getLastNameA());
				return b;
			})
			.collect(toList());
	}
	
}

// -------- fake code ---------
interface ARepo {
	List<A> findAll(); 
}

class A {
	@Getter @Setter private String firstNameA;
	@Getter @Setter private String lastNameA;
}
class B {
	@Getter @Setter private String firstNameB;
	@Getter @Setter private String lastNameB;
}
