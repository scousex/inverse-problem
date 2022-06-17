package asu.tusur.profitinverseproblem.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
   // long productID;
    String productName;
    String productDescription;
    Double productCost;
    Double productPrice;
    Double sells;
    Double profit;

    public Product(String productName, Double productCost, Double productPrice, Double sells) {
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.sells = sells;
        this.profit = sells * (productPrice - productCost);
    }

    /* public String toString(){
        return "productName: "+ getProductName()+"\n"
                +"productDescription"+getProductDescription()+"\n"
                +"productCost: "+ getProductCost() +"\n"
                +"productPrice"+getProductPrice();
    }
*/
}
