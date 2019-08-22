package victor.training.jpa.app.common.data;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class EntityRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
    extends JpaRepositoryFactoryBean<R, T, ID> {
	
//    public EntityRepositoryFactoryBean(JpaEntityInformation jpaEntityInformation , EntityManager em) {
//        super();
//        setEntityManager(em);
//    }
//    
	
    public EntityRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@SuppressWarnings("rawtypes")
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new EntityRepositoryFactory(em);
    }
    
    private static class EntityRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
        private final EntityManager em;

        public EntityRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        protected Object getTargetRepository(RepositoryInformation metadata) {
            return new EntityRepositoryImpl<T, ID>((Class<T>)metadata.getDomainType(), em);
        }   
        
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return EntityRepositoryImpl.class;
        }
        
    }
    
}
