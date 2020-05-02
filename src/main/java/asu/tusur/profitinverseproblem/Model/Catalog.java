package asu.tusur.profitinverseproblem.Model;

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
}
