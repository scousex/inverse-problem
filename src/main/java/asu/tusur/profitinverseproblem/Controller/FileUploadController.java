package asu.tusur.profitinverseproblem.Controller;

import asu.tusur.profitinverseproblem.Model.*;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import asu.tusur.profitinverseproblem.Repository.StorageService;
import asu.tusur.profitinverseproblem.exceptions.CalculateException;
import asu.tusur.profitinverseproblem.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);
        //redirectAttributes.addFlashAttribute("message","Успешно загружен файл "+file.getOriginalFilename());
        redirectAttributes.addAttribute("filename",file.getOriginalFilename());
        return "redirect:/load";
    }

    @GetMapping("/load")
    public RedirectView loadFile(@RequestParam("filename") String filename, RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/productsInfo.html",true);
        try{
            CostFileProcessing costFileProcessor = new CostFileProcessing();
            Catalog catalog = new Catalog();
            catalog.setProducts(costFileProcessor.getProducts(filename));
            List<Goals> goalsList = new ArrayList<Goals>(catalog.getProducts().size());
            catalog.getProducts().stream()
                    .forEach(product -> {goalsList.add(new Goals());});
            System.out.println(catalog.toString());
           // redirectAttributes.addFlashAttribute("products",catalog.getProducts());\

            redirectAttributes.addFlashAttribute("products",catalog.getProducts());
            redirectAttributes.addFlashAttribute("goals",goalsList);
            String profit = "Полученная прибыль: "+catalog.getProfit().setScale(2);
            redirectAttributes.addFlashAttribute("profit",profit);
            //redirectAttributes.addAttribute("goal",new ProductGoalsWrapper());
        }catch (Exception e){
            System.out.println("Ошибка при обработке файла: "+e.getMessage());
            throw new StorageException(e.getMessage());
        }
        return redirectView;
    }

    @PostMapping(value = "/recomend")
    public RedirectView recomend(@RequestParam("filename") String filename,
                                 //@ModelAttribute ProductGoalsWrapper wrapper,
                                 RedirectAttributes redirectAttributes) throws CalculateException {
        RedirectView redirectView = new RedirectView("/recomendations.html",true);
        try{
            System.out.println("FIlename: " + filename);


            CostFileProcessing costFileProcessor = new CostFileProcessing();
            List<Product> products = (costFileProcessor.getProducts(filename));
            Catalog catalog = new Catalog();
            catalog.setProducts(products);
            List<Goals> goals = new ArrayList<>();
            goals.add(new Goals(0.2,0.7,0.1,catalog.getProfit().add(BigDecimal.valueOf(1200*0.3))));
            goals.add(new Goals(0.2,0.6,0.2,catalog.getProfit().add(BigDecimal.valueOf(1200*0.3))));
            goals.add(new Goals(0.3,0.5,0.2,catalog.getProfit().add(BigDecimal.valueOf(1200*0.4))));
            String profit = ""+ catalog.getProfit();
            List<Recommendation> recommendations = new ArrayList<>();
            BigDecimal p = BigDecimal.valueOf(0.0);
            for (Recommendation recomend: recommendations) {
                p.add(recomend.getProfitRecom());
            }
            for(int i = 0; i<products.size();i++){
                recommendations.add(new Recommendation(products.get(i).getProductName(),
                                                       products.get(i).getProductCost(),
                                                       products.get(i).getProductPrice(),
                                                       goals.get(i)));
            }
            redirectAttributes.addFlashAttribute("products", recommendations);
            redirectAttributes.addFlashAttribute("profit",profit);
            redirectAttributes.addFlashAttribute("recProfit",p);
        }catch (Exception e){
            System.out.println("Ошибка при обработке файла: "+e.getMessage());
            throw new StorageException(e.getMessage());
        }
        return redirectView;
    }
}
