package asu.tusur.profitinverseproblem.Controller;

import asu.tusur.profitinverseproblem.Model.*;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import asu.tusur.profitinverseproblem.Repository.StorageService;
import asu.tusur.profitinverseproblem.Service.CalculationService;
import asu.tusur.profitinverseproblem.exceptions.CalculateException;
import asu.tusur.profitinverseproblem.exceptions.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class FileUploadController {

    private final StorageService storageService;
    private final CalculationService calculationService;

    private final CostFileProcessing costFileProcessor;

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
            Catalog catalog = new Catalog();
            catalog.setProducts(costFileProcessor.getProducts(filename));
            List<Goal> goalList = new ArrayList<Goal>(catalog.getProducts().size());
            catalog.getProducts().stream()
                    .forEach(product -> {
                        goalList.add(new Goal());
                    });
            System.out.println(catalog.toString());
           // redirectAttributes.addFlashAttribute("products",catalog.getProducts());\
            ProductGoalsWrapper wrapper =
                    new ProductGoalsWrapper(catalog.getProducts(), goalList,
                                            BigDecimal.ZERO);
            redirectAttributes.addFlashAttribute("wrapper", wrapper);
            redirectAttributes.addFlashAttribute("products",catalog.getProducts());
            redirectAttributes.addFlashAttribute("goals", goalList);
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
                                 @ModelAttribute ProductGoalsWrapper wrapper,
                                 RedirectAttributes redirectAttributes) throws CalculateException {
        RedirectView redirectView = new RedirectView("/recomendations.html",true);
        try{
            System.out.println("FIlename: " + filename);
            List<Goal> goals = wrapper.getGoals();

            CostFileProcessing costFileProcessor = new CostFileProcessing();
            List<Product> products =  costFileProcessor.getProducts(filename);
            List<Recommendation> recommendations =
                    calculationService.calculateRecommendations(products, goals, wrapper.getProfitGoal());

            redirectAttributes.addFlashAttribute("products",recommendations);
        }catch (Exception e){
            System.out.println("Ошибка при обработке файла: "+e.getMessage());
            throw new StorageException(e.getMessage());
        }
        return redirectView;
    }
}
