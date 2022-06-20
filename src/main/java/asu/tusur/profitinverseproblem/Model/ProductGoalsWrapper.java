package asu.tusur.profitinverseproblem.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class ProductGoalsWrapper {
    ArrayList<Product> products;
    ArrayList<Goals> goals;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Goals> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<Goals> goals) {
        this.goals = goals;
    }
}
