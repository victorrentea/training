package victor.training.spring.basic.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import victor.training.spring.basic.model.InterviewQuestion;

@Component // SOLUTION
public class InterviewDaoImpl extends BaseDao implements InterviewDao {
	
	@Override
	public List<InterviewQuestion> getAllQuestions() {
		return Collections.emptyList();
	}

}
