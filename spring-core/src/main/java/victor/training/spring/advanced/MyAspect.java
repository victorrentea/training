package victor.training.spring.advanced;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

// @Component ?
@Aspect
public class MyAspect {

	@Before("execution(* remove*(..))")
	public void auditRemoval() {
		System.out.println("An entity is going to be removed...");
	}

	@Around("execution(* victor.training..*Dao*.get*(..))")
	public Object auditCallDuration(ProceedingJoinPoint point) throws Throwable {
		long t0 = System.currentTimeMillis();
		Object value = point.proceed();
		long t1 = System.currentTimeMillis();
		
		System.out.println("Call "+
				point.getSignature().getDeclaringTypeName() + "."
				+ point.getSignature().getName() + "("+
				Arrays.toString(point.getArgs())+") took " + (t1 - t0));
		return value;
	}
}
