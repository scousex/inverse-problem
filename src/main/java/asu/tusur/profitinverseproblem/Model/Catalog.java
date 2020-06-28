package asu.tusur.profitinverseproblem.Model;

import java.math.BigDecimal;
import java.util.List;

public class Catalog {

    List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){

        if(product!=null)
            products.add(product);
    }

    @Override
    public String toString(){

        String list = "#\t"+"Name\t"+"Cost\t"+"Price\n";
        int num = 0;

        for (Product product: products
             ) {
            list += (++num) +" " + product.getProductName() + " " +
                    product.getProductCost() + " " + product.getProductPrice() + "\n";
        }

        return list;
    }

    public BigDecimal getProfit(){
        BigDecimal profit = BigDecimal.valueOf(0);
        for (Product product:products) {
            profit.add(BigDecimal.valueOf(product.getProfit()));
        }
        return profit;
    }
}
