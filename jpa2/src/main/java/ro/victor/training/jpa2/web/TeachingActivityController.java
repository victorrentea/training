package ro.victor.training.jpa2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.victor.training.jpa2.facade.TheFacade;

@RestController
@RequestMapping("/api/activities")
public class TeachingActivityController {

	@Autowired
	private TheFacade facade;
}
