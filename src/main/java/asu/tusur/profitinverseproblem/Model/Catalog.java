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

        String list = "#\t"+"Name\t"+"Cost\t"+"Price\t"+"Sells\t"+"\n";
        int num = 0;

        for (Product product: products
             ) {
            list += (++num) +" " + product.getProductName() + " " +
                    product.getProductCost() + " " + product.getProductPrice() + " " +
                    +product.getSells() + "\n";
        }

        return list;
    }

    public BigDecimal getProfit(){
//        BigDecimal profit = BigDecimal.valueOf(0);
        return products.parallelStream()
                .map(Product::getProfit)
                .reduce((Double::sum))
                .map(BigDecimal::valueOf)
                .orElse(BigDecimal.ZERO);
//        for (Product product:products) {
//           profit = profit.add(BigDecimal.valueOf(product.getProfit()));
//        }
//        return profit;
    }
}
