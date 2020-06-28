package asu.tusur.profitinverseproblem.Model;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
   // long productID;
    String productName;
    String productDescription;
    Double productCost;
    Double productPrice;
    int sells;
    Double profit;
    BigDecimal productCostRecom;
    BigDecimal productPriceRecom;
    int sellsRecom;
    BigDecimal profitRecom;

    public Product(String productName, Double productCost, Double productPrice) {
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.sells = productCost.intValue() + 15;
        this.profit = (sells*productPrice - sells*productCost);
        this.productCostRecom = BigDecimal
                .valueOf(0.0)
                .setScale(2, RoundingMode.CEILING);
        this.productPriceRecom = BigDecimal
                .valueOf(0.0)
                .setScale(2,RoundingMode.CEILING);
        this.sellsRecom = 0;
        this.profitRecom =BigDecimal
                .valueOf(0.0)
                .setScale(2, RoundingMode.CEILING);

    }
    /* getters and setters */
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSells() {
        return sells;
    }

    public Double getProfit() {
        return profit;
    }

    public BigDecimal getProductCostRecom() {
        return productCostRecom;
    }

    public BigDecimal getProductPriceRecom() {
        return productPriceRecom;
    }

    public int getSellsRecom() {
        return sellsRecom;
    }

    public BigDecimal getProfitRecom() {
        return profitRecom;
    }

    /* public String toString(){
        return "productName: "+ getProductName()+"\n"
                +"productDescription"+getProductDescription()+"\n"
                +"productCost: "+ getProductCost() +"\n"
                +"productPrice"+getProductPrice();
    }
*/
}
