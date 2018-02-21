package ro.victor.unittest.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service1 {

	@Autowired
	private ReportingRepo repoA;
	
	@Autowired
	private NotificationRepo repoB;	
	
}
