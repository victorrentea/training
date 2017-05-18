package victor.training.spring.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import victor.training.spring.dao.EmployeeDao;
import victor.training.spring.dao.InterviewDao;
import victor.training.spring.model.Employee;
import victor.training.spring.model.InterviewQuestion;

public class HRService {
	@Autowired // SOLUTION
	private EmployeeDao employeeDao;
	@Autowired // SOLUTION
	private InterviewDao interviewDao;
	@Autowired @Qualifier("handshakeConverter") // SOLUTION
	private CurrencyConverter currencyConverter;
	private MyWSClient webServiceClient;
	private String myProperty;

	public Employee getEmployeeById(String employeeId) {
		return employeeDao.getById(employeeId);
	}
	
	public List<InterviewQuestion> getAllQuestions() {
		return interviewDao.getAllQuestions();
	}

	public void createEmployee(Employee newEmployee) {
		employeeDao.persist(newEmployee);
	}

	public void removeEmployee(String employeeId) {
		employeeDao.removeById(employeeId);
	}
	
	public void callMyService(Date date) {
		webServiceClient.callWebService(new SimpleDateFormat("yyyy-MM-dd").format(date));
	}

	public void switchPhones(String employee1Id, String employee2Id) {
		throw new UnsupportedOperationException();
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void setInterviewDao(InterviewDao interviewDao) {
		this.interviewDao = interviewDao;
	}

	public void setCurrencyConverter(CurrencyConverter currencyConverter) {
		this.currencyConverter = currencyConverter;
	}

	public void setMyProperty(String myProperty) {
		this.myProperty = myProperty;
	}

	public void setWebServiceClient(MyWSClient webServiceClient) {
		this.webServiceClient = webServiceClient;
	}
	
	public String getMyProperty() {
		return myProperty;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
