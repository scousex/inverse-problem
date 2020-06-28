package asu.tusur.profitinverseproblem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {

    @GetMapping("/calculations.html")
    public String getCalculations(Model model)
    {
        model.addAttribute("products",null);
        return "/calculations.html";
    }
    @GetMapping("/productsInfo.html")
    public String getProductsInfo(Model model)
    {
        return "/calculations.html";
    }
    @GetMapping("/recomendations.html")
    public String getRecomendations(){
        return "/recomendations.html";
    }
    @GetMapping("/authors.html")
    public String getAuthors(){
        return "/authors.html";
    }
    @GetMapping("/info.html")
    public String getInfo(){
        return "/info.html";
    }
    @GetMapping("/index.html")
    public String getIndex(){
        return "/index.html";
    }
    @GetMapping("/error.html")
    public String getError(){
        return "/error.html";
    }
}
