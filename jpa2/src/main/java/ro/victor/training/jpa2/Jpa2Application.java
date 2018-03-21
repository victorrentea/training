package ro.victor.training.jpa2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.victor.training.jpa2.common.data.EntityRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableTransactionManagement
@EnableJpaAuditing
public class Jpa2Application {

	@Autowired
	private DummyDataCreator dummyDataCreator;
	@Autowired
	private VoxxedDaysPlayground playground;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Application started. Running playground code...");
//		dummyDataCreator.persistDummyData();
		System.out.println(" ========= FIRST TRANSACTION ========== ");
		playground.firstTransaction();
		System.out.println(" ========= SECOND TRANSACTION ========== ");
		playground.secondTransaction();
		System.out.println(" ========= END ========== ");
	}
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Jpa2Application.class, args);
	}
}
