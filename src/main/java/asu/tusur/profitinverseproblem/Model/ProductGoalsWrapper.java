package asu.tusur.profitinverseproblem.Model;

import lombok.Data;

import java.util.List;

@Data
public class ProductGoalsWrapper {
    List<Product> products;
    List<Goals> goals;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }
}
