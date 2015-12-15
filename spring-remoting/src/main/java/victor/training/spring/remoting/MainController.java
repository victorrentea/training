package victor.training.spring.remoting;

import java.util.List;
import java.util.Random;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import victor.training.spring.remoting.queue.QuestionSender;
import victor.training.spring.remoting.topic.Baba;

@RestController
public class MainController {
	private final static Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private QuestionSender queueSender;
	
	@Autowired
	private List<Baba> babe;
	
	@RequestMapping("/queue")
	@ResponseBody
	public String testQueue() throws JMSException {
		String result = queueSender.sendQuestion();
		return "Queue test: got back: " + result;
	}

	@RequestMapping("/topic")
	@ResponseBody
	public String testTopic() {
		Baba randomBaba = babe.get(new Random().nextInt(babe.size()));
		log.debug("Telling baba {} the breaking news", randomBaba);
		randomBaba.broadcastGossip("Breaking News: S-a daramat hornul lui Mitica !!");
		return "Breaking News Published! Check the server log.";
	}

}
