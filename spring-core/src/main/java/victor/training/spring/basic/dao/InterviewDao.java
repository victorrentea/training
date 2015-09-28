package victor.training.spring.basic.dao;

import java.util.List;

import victor.training.spring.basic.model.InterviewQuestion;

public interface InterviewDao {

	List<InterviewQuestion> getAllQuestions();
}
