package ro.victor.training.jpa2;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.victor.training.jpa2.common.data.EntityRepositoryFactoryBean;
import ro.victor.training.jpa2.util.MyUtil;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityRepositoryFactoryBean.class)
@EnableTransactionManagement
@EnableJpaAuditing
public class Jpa2Application {

	@Autowired
	private DummyDataCreator dummyDataCreator;

	@PostConstruct
	public void persistDummyData() {
		dummyDataCreator.persistDummyData();
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return MyUtil::getUserOnCurrentThread;
	}

	public static void main(String[] args) {
		SpringApplication.run(Jpa2Application.class, args);
	}
}
