package victor.training.spring.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import victor.training.spring.model.InterviewQuestion;

@Repository // SOLUTION
public class InterviewDaoImpl extends BaseDao implements InterviewDao {
	
	@Override
	public List<InterviewQuestion> getAllQuestions() {
		return Collections.emptyList();
	}

}
