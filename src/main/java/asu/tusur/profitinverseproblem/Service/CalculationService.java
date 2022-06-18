package asu.tusur.profitinverseproblem.Service;

import asu.tusur.profitinverseproblem.Model.*;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import asu.tusur.profitinverseproblem.Repository.StorageService;
import asu.tusur.profitinverseproblem.exceptions.CalculateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private final StorageService storageService;
    private final CostFileProcessing costFileProcessor;

    public List<Recommendation> calculateRecommendations (List<Product> products, List<Goals> goals) throws
            CalculateException, Exception {
        Catalog catalog = new Catalog();
        catalog.setProducts(products);
//        goals.add(
//                new Goals(0.2, 0.7, 0.1, catalog.getProfit().add(BigDecimal.valueOf(1200 * 0.3))));
//        goals.add(
//                new Goals(0.2, 0.6, 0.2, catalog.getProfit().add(BigDecimal.valueOf(1200 * 0.3))));
//        goals.add(
//                new Goals(0.3, 0.5, 0.2, catalog.getProfit().add(BigDecimal.valueOf(1200 * 0.4))));
        List<Recommendation> recommendations = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            try {
                Product product = products.get(i);
                Goals goal = goals.get(i);
                recommendations.add(getRecommendation(product, goal));
            } catch (Exception e) {
                throw new CalculateException("Ошибка при расчёте рекомендаций");
            }
        }
        return recommendations;
    }

    private Recommendation getRecommendation (Product product, Goals goal) {
        double dif =
                Maths.Newton.minimize(0, goal.getPrice().doubleValue(),
                                      goal.getCost().doubleValue(),
                                      goal.getSells(),
                                      product.getSells(),
                                      product.getProductPrice().doubleValue(),
                                      product.getProductCost().doubleValue(),
                                      goal.getProfit().subtract(
                                              BigDecimal.valueOf(
                                                      product.getProfit())).doubleValue());
        BigDecimal recomPr = product.getProductPrice().add(BigDecimal.valueOf(dif));
        BigDecimal productPriceRecom = recomPr
                .setScale(2, RoundingMode.CEILING);
        BigDecimal productCostRecom =
                (BigDecimal.valueOf(dif).multiply(goal.getCost().divide(goal.getPrice())))
                        .setScale(2, RoundingMode.CEILING)
                        .add(product.getProductCost());
        double sellsRecom = BigDecimal.valueOf(dif).multiply(
                        BigDecimal.valueOf(goal.getSells()).divide(goal.getPrice()))
                .add(BigDecimal.valueOf(product.getSells())).setScale(0,
                                                                      RoundingMode.HALF_UP).doubleValue();
        BigDecimal profitRecom =
                (productPriceRecom
                        .multiply(BigDecimal.valueOf(sellsRecom))
                        .subtract(productCostRecom
                                          .multiply(BigDecimal.valueOf(sellsRecom)))
                ).setScale(2, RoundingMode.CEILING);
        return Recommendation.builder()
                .productCostRecom(productCostRecom)
                .productPriceRecom(productPriceRecom)
                .sellsRecom(sellsRecom)
                .build();
    }
}
