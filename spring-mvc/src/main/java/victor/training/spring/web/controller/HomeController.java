package victor.training.spring.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JREmptyDataSource;

@Controller
@RequestMapping("/")
public class HomeController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	
	@RequestMapping
	public String showHomePage() {
		return "homePage";
	}
	
	@RequestMapping(value = "first.pdf", produces = "application/pdf")
	public ModelAndView exportAPdf(ModelMap modelMap) {
		modelMap.put("datasource", new JREmptyDataSource());
		modelMap.put("format", "pdf");
		return new ModelAndView("FirstJasper.jasper", modelMap);
	}
}
