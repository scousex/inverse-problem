package asu.tusur.profitinverseproblem.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Value("${index.authorization}")
    private String authorization;

    @GetMapping(value = "/")
    public String main(Model model){

        model.addAttribute("authorization",authorization);
        model.addAttribute("solve","давайте решать вашу задачу" );
        model.addAttribute("username","username");
        model.addAttribute("password","password");
        model.addAttribute("repassword","repassword");
        return "index";
    }
}
