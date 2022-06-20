package asu.tusur.profitinverseproblem.Model;


import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    private long id;
    private String productName;
    private String productDescription;
    private BigDecimal productCost;
    private BigDecimal productPrice;
    private Double sells;
    @Getter(value = AccessLevel.PRIVATE)
    private Double profit;
    private BigDecimal productCostRecom;
    private BigDecimal productPriceRecom;
    private Double sellsRecom;
    private BigDecimal profitRecom;



    public Double getProfit(){
       return sells * (productPrice.subtract(productCost).doubleValue());
    }


}
