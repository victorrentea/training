package victor.training.jpa.facade;

import static java.util.stream.Collectors.joining;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Aspect
@Component
public class FacadeLoggingInterceptor {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public Logger log = LoggerFactory.getLogger(FacadeLoggingInterceptor.class);

	@PostConstruct
	public void configureMapper() {
		if (log.isTraceEnabled()) {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
	}
	
	@Around("execution(* victor..*Facade.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logBefore(joinPoint);
		
		Object returnedObject;
		try {
			returnedObject = joinPoint.proceed();
		} catch (Exception e) {
			log.error("Exception: {}: {}", e.getClass(), e.getMessage());
			throw e;
		}
		
		logAfter(joinPoint, returnedObject);
		return returnedObject;
	}

	private void logBefore(ProceedingJoinPoint joinPoint) {
		if (log.isDebugEnabled()) {
			String separator = log.isTraceEnabled() ? "\n" : ", ";
			String argListConcat = Stream.of(joinPoint.getArgs())
					.map(this::toString)
					.map(Object::toString)
					.collect(joining(separator));

			log.debug("Invoking: {}(..): {}", joinPoint.getSignature().getName(), argListConcat);
		}
	}
	
	private String toString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.warn("Could not serialize as JSON: "+e);
			return "<ERR>";
		}
	}
	
	private void logAfter(ProceedingJoinPoint joinPoint, Object returnedObject) {
		if (log.isDebugEnabled()) {
			log.debug("Return from {}: {}", joinPoint.getSignature().getName(), toString(returnedObject));
		}
	}
}
