package victor.training.spring.web.controller;


import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.spring.jms.QuestionSender;

@RestController
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired
	private QuestionSender sender;
	
	
	@RequestMapping
	public String showHomePage() {
		log.debug("Hello Ma'!");
		return "I'm here";
	}
	
	@RequestMapping("jms")
	public String testJms() throws JMSException {
		return sender.sendQuestion();
	}
}
