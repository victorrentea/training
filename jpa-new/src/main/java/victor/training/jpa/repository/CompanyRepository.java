package victor.training.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import victor.training.jpa.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
