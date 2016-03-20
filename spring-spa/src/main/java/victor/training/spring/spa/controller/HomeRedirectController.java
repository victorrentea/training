package victor.training.spring.spa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeRedirectController {
	
	@RequestMapping("/")
	public String redirectToIndex() {
		return "redirect:/app/index.html";
	}

}
