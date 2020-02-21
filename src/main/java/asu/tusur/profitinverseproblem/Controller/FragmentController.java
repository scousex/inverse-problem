package asu.tusur.profitinverseproblem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {

    @GetMapping("/calculations.html")
    public String getCalculations(){
        return "/calculations.html";
    }
}
