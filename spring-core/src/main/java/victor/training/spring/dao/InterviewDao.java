package victor.training.spring.dao;

import java.util.List;

import victor.training.spring.model.InterviewQuestion;

public interface InterviewDao {

	List<InterviewQuestion> getAllQuestions();
}
