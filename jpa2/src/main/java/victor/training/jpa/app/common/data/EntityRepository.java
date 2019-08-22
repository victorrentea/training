package victor.training.jpa.app.common.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    
    T getReference(ID id);
    void detach(T oldRecord);

    T getExactlyOne(ID id);
    
    List<T> getByPrimaryKeys(Collection<ID> ids);

}
