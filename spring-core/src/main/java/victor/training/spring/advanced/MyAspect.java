package victor.training.spring.advanced;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {

	@Before("execution(* remove*(..))")
	public void auditRemoval() {
		System.out.println("An entity is going to be removed...");
	}

	@Around("execution(* spring..*DAO*.get*(..))")
	public Object auditCallDuration(ProceedingJoinPoint point) throws Throwable {
		long t0 = System.currentTimeMillis();
		Object value = point.proceed();
		long t1 = System.currentTimeMillis();
		
		System.out.println("Call " + point.getSignature().getName() + " took " + (t1 - t0));
		return value;
	}
}
