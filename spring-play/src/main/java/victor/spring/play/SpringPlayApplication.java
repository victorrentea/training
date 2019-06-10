package victor.spring.play;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@EnableCaching
@SpringBootApplication
public class SpringPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPlayApplication.class, args);
	}

//	@Bean
//    public LocalValidatorFactoryBean validatorFactoryBean() {
//	    return new LocalValidatorFactoryBean();
//    }


	@Autowired
	private DoiB b;

	@Bean
	static public BeanPostProcessor bpp() {
//		System.out.println("b="+b);
		return new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				System.out.println("Seen: " + beanName );
				return null;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				return null;
			}
		};
	}

	@Bean
	public WithClose withClose() {
		return new WithClose();
	}
}

@Profile("local")
@Retention(RetentionPolicy.RUNTIME)
@interface ProfileLocal {

}

@ProfileLocal
class WithClose implements CommandLineRunner {
	public void shutdown() {
		System.out.println("Bye!");
	}
	@Autowired
	private ApplicationContext context;

	@Autowired
	private DoiB b;
	@Autowired
    private Validator validator;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(context.getBean("name"));
		System.out.println(context.getBean("doiB"));
		b.go();
		b.go();

		validator.validate(new Record("a"));
	}
}

@Service
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
	@AliasFor(annotation = Service.class, attribute = "value")
	String value() default "";
}

//@Profile("default")
@Facade("name")
class Nameless {

}





@Component
@RequiredArgsConstructor
class Injection {
//	@Autowired
//	private B b;

	private final B b;


	@PostConstruct
	public void runme() {
		System.out.println("B:" + b);
	}
}


@Component
class B {}