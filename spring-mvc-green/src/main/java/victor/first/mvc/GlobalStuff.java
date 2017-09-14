package victor.first.mvc;

import java.util.Date;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalStuff {

	@ModelAttribute("serverTime")
	public Date getServerTime() {
		return new Date();
	}
	
}
