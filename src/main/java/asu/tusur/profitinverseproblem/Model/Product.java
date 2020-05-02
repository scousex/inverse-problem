package asu.tusur.profitinverseproblem.Model;


public class Product {
   // long productID;
    String productName;
    String productDescription;
    double productCost;
    double productPrice;

    public Product(String productName, double productCost, double productPrice) {
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
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

   /* public String toString(){
        return "productName: "+ getProductName()+"\n"
                +"productDescription"+getProductDescription()+"\n"
                +"productCost: "+ getProductCost() +"\n"
                +"productPrice"+getProductPrice();
    }
*/
}
