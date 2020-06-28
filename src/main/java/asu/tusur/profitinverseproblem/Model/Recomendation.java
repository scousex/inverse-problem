package asu.tusur.profitinverseproblem.Model;

import asu.tusur.profitinverseproblem.exceptions.CalculateException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Recomendation extends Product {
    public Recomendation(String productName, Double productCost, Double productPrice, Goals goals) throws CalculateException {
        super(productName, productCost, productPrice);
        Double a = goals.price;
        Double b = goals.cost;
        Double c = goals.sells;
        Double deltaP = goals.profit - this.profit;
//        double dif = Maths.Bolzano.minimize(
//                -productPrice,productPrice,a,b,c,sells,productPrice,productCost, goals.profit
//        );
        try {
            double dif =
                    Maths.Newton.minimize(0, a, b, c, sells, productPrice, productCost, goals.profit - profit);
            double recomPr = this.productPrice + dif;
            this.productPriceRecom = BigDecimal
                    .valueOf(recomPr)
                    .setScale(2, RoundingMode.CEILING);
            this.productCostRecom =
                    BigDecimal.valueOf((dif * (b / a)))
                            .setScale(2, RoundingMode.CEILING)
                            .add(BigDecimal.valueOf(this.productCost));
            this.sellsRecom = BigDecimal.valueOf(dif * (c / a))
                    .add(BigDecimal.valueOf(this.sells)).intValue();
            this.profitRecom =
                    (productPriceRecom
                            .multiply(BigDecimal.valueOf(sellsRecom))
                            .subtract(productCostRecom
                                    .multiply(BigDecimal.valueOf(sellsRecom)))
                    ).setScale(2, RoundingMode.CEILING);
        }catch (Exception e){
            throw new CalculateException("Ошибка при расчёте рекомендаций");
        }
    }
}
