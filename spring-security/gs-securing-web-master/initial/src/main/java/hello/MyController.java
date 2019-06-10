package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
    @Value("${a}")
    private String a;

//    @GetMapping
//    public String root() {
//        System.out.println("XX:"+a);
//        return "home";
//    }
}
