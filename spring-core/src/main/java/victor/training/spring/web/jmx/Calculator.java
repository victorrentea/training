package victor.training.spring.web.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="bean:name=calculator")
public class Calculator {

	@ManagedOperation
	public int sum(int x, int y) {
		return x+y;
	}

}
