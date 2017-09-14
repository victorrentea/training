package victor.first.mvc;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import victor.first.domain.User;
import victor.first.domain.UserService;

@Controller
@RequestMapping("/bani")
public class FirstController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = GET)
	@ResponseBody
	public String m() {
		return "Hello from Spring MVC";
	}
	
//	@RequestMapping(method = GET, path = "/publici")
//	public String fur(HttpServletRequest request) {  
//		User user = userService.getUserById();
//		request.setAttribute("user", user); 
//		return "hello";
//	}
	
//	@RequestMapping(method = GET, path = "/publici")
//	public String fur(Map<String, Object> model) {  
//		User user = userService.getUserById();
//		model.put("user", user); 
//		// tot ce pui in mapa goala pe care ti-o da Springul, Springul le va copia in request.setAttribute
//		return "hello";
//	}
	
	
	
	@RequestMapping(method = GET, path = "/publici")
	public String fur(Map<String, Object> model) {  
		User user = userService.getUserById();
		model.put("user", user); 
		// tot ce pui in mapa goala pe care ti-o da Springul, Springul le va copia in request.setAttribute
		return "hello";
	}
	
	@RequestMapping(method = GET, path = "/create")
	public String openCreaza() {
		return "create";		
	}
	
	@RequestMapping(method = POST, path = "/create")
	public String creaza(@Valid User user) {
//		User user = new User();
//		user.set request.getParameter("firstName");
		System.out.println("Am primit"
				+ "o cerere de creeare: " + user);
		return "redirect:/bani";
	}
}
