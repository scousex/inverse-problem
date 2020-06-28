package asu.tusur.profitinverseproblem.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StorageException.class)
    protected String handleStorageException(StorageException ex, Model model){
        model.addAttribute("errorMessage","Ошибка при работе с файлом: "+ex.getMessage());
        return "error.html";
    }
}
