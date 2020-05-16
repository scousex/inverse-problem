package asu.tusur.profitinverseproblem.Model;


import java.math.BigDecimal;

public class Product {
   // long productID;
    String productName;
    String productDescription;
    double productCost;
    double productPrice;
    int sells;
    BigDecimal profit;
    double productCostRecom;
    double productPriceRecom;
    int sellsRecom;
    BigDecimal profitRecom;

    public Product(String productName, double productCost, double productPrice) {
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.sells = (int) productCost + 15;
        this.profit = BigDecimal.valueOf(sells*productPrice - sells*productCost);
        this.productCostRecom = this.productCost + (this.productCost-productPrice)/5;
        this.productPriceRecom = this.productPrice + (this.productCost-productPrice)/3;
        this.sellsRecom = this.sells + (int)(this.sells - Math.abs(Math.sqrt(sells)));
        this.profitRecom = BigDecimal.valueOf(sellsRecom*productPriceRecom - sellsRecom*productCostRecom);


    }
    /* getters and setters */
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
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

    public BigDecimal getProfit() {
        return profit;
    }

    public double getProductCostRecom() {
        return productCostRecom;
    }

    public double getProductPriceRecom() {
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
