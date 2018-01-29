package ro.victor.training.jpa2;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.victor.training.jpa2.common.data.EntityRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableTransactionManagement
public class Jpa2Application {
	
	@Autowired
	private DummyDataCreator dummyDataCreator;
	
	@PostConstruct
	public void persistDummyData(){
		dummyDataCreator.persistDummyData();
		
		
	}
	

	public static void main(String[] args) {
		SpringApplication.run(Jpa2Application.class, args);
	}
}
