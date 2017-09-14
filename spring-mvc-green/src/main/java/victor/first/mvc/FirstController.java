package victor.first.mvc;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bani")
public class FirstController {
	
	@RequestMapping(method = GET)
	@ResponseBody
	public String m() {
		return "Hello from Spring MVC";
	}
	
	@RequestMapping(method = GET, path = "/publici")
	public String fur() {
		return "hello";
	}

}
