package ro.victor.unittest.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication	
public class MvcApplication implements WebMvcConfigurer{
	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(headShotInterceptor()).addPathPatterns("/**");
	}
	@Bean
	public HandlerInterceptor headShotInterceptor() {
		return new HandlerInterceptor() {
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
				System.out.println("INTERCEPTOR RUNS");				
				response.addHeader("Head-Shot", "true");
				return true;
			}
		}; 
	}
}

@RestController
class RestInPeaceController {
	@Autowired
	private RestInPeaceService service;
	
	@GetMapping("/peace/{ssn}")
	public Peace peace(@PathVariable String ssn) {
		return service.getPeace(ssn);
	}
}

class Peace {
	public String ssn;

	public Peace(String ssn) {
		this.ssn = ssn;
	}
}

@Service
class RestInPeaceService {

	public Peace getPeace(String ssn) {
		return new Peace(ssn.toUpperCase()); 
	}
	
}
