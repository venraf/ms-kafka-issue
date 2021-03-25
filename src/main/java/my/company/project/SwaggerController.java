package my.company.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class SwaggerController {
    @GetMapping(value = "/s")
    public String index() {
        return "redirect:swagger-ui/";
    }
}
