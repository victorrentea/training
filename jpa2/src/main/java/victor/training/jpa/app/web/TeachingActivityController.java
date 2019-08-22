package victor.training.jpa.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.facade.TheFacade;

@RestController
@RequestMapping("/api/activities")
public class TeachingActivityController {

	@Autowired
	private TheFacade facade;
}
