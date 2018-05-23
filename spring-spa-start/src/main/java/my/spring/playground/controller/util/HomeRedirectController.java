package my.spring.playground.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeRedirectController {
	
	@RequestMapping("/")
	public String redirectToIndex() {
		return "redirect:/index.html";
	}

}
