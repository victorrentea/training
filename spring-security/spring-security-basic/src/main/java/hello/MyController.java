package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class MyController {
    @Value("${a}")
    private String a;

    @GetMapping("/transfer")
    @ResponseBody
    public String transferMoney(@RequestParam String accountNo, @RequestParam String amount) {
        log.debug("Transferring EUR {} to account {}", amount, accountNo);
        return "CAT!";
    }

    @GetMapping("redirect")
    public String redirect() {
        return "redirect:https://myhost.com/some/arbitrary/path";
    }

    @GetMapping("locale")
    @ResponseBody
    public String locale(HttpServletRequest request) {
        return new RequestContext(request).getLocale().toString();
    }

    @GetMapping("date")
    @ResponseBody
    public String convertDate(@RequestParam Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
