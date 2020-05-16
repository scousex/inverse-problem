package asu.tusur.profitinverseproblem.Controller;

import asu.tusur.profitinverseproblem.Model.Catalog;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import asu.tusur.profitinverseproblem.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;

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
        RedirectView redirectView = new RedirectView("/calculations.html",true);
        try{
            CostFileProcessing costFileProcessor = new CostFileProcessing();
            Catalog catalog = new Catalog();
            catalog.setProducts(costFileProcessor.getProducts(filename));
            System.out.println(catalog.toString());
           // redirectAttributes.addFlashAttribute("products",catalog.getProducts());\

            redirectAttributes.addFlashAttribute("products",catalog.getProducts());
        }catch (Exception e){
            System.out.println("Ошибка при обработке файла: "+e.getMessage());
        }
        return redirectView;
    }

    @GetMapping("/recomend")
    public RedirectView recomend(@RequestParam("filename") String filename,RedirectAttributes redirectAttributes){
        RedirectView redirectView = new RedirectView("/recomendations.html",true);
        try{
            CostFileProcessing costFileProcessor = new CostFileProcessing();
            Catalog catalog = new Catalog();
            catalog.setProducts(costFileProcessor.getProducts(filename));
            System.out.println(catalog.toString());
            redirectAttributes.addFlashAttribute("products",catalog.getProducts());
        }catch (Exception e){
            System.out.println("Ошибка при обработке файла: "+e.getMessage());
        }
        return redirectView;
    }
}
