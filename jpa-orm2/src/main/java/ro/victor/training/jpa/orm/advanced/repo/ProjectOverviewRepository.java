package ro.victor.training.jpa.orm.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import ro.victor.training.jpa.orm.advanced.entity.ProjectOverview;

public interface ProjectOverviewRepository extends JpaRepository<ProjectOverview, String> {

	@Procedure(procedureName = "plus1inout", outputParameterName="res")
	Integer procedure_test(@Param("arg") Integer arg);
}
