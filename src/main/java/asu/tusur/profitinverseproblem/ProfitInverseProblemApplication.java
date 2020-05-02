package asu.tusur.profitinverseproblem;

import asu.tusur.profitinverseproblem.Model.Catalog;
import asu.tusur.profitinverseproblem.Model.Product;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@EnableEurekaClient
@SpringBootApplication
public class ProfitInverseProblemApplication {
    public static void main(String[] args) { SpringApplication.run(ProfitInverseProblemApplication.class, args);}
}
