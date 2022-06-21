package asu.tusur.profitinverseproblem.Service;

import asu.tusur.profitinverseproblem.Model.*;
import asu.tusur.profitinverseproblem.Repository.CostFileProcessing;
import asu.tusur.profitinverseproblem.Repository.StorageService;
import asu.tusur.profitinverseproblem.exceptions.CalculateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculationService {

    private final StorageService storageService;
    private final CostFileProcessing costFileProcessor;

    public List<Recommendation> calculateRecommendations (List<Product> products,
                                                          List<Goal> goals,
                                                          BigDecimal targetProfit) throws
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
                Goal goal = goals.get(i);
                this.calculatePriceGoal(goal, product, catalog.getProfit(), targetProfit);
                recommendations.add(getRecommendation(product, goal));
                this.roundingSells(recommendations, goals);
                this.minimizeRoundedProfits(recommendations, goals);
            } catch (Exception e) {
                throw new CalculateException("Ошибка при расчёте рекомендаций");
            }
        }
        log.info("Total profit by recommendations: " + recommendations.stream()
                .map(Recommendation::getProfitRecom)
                .reduce(BigDecimal::add));
        return recommendations;
    }

    private void calculatePriceGoal (Goal goal,
                                     Product product,
                                     BigDecimal totalProfit,
                                     BigDecimal totalTargetProfit) {
        goal.setProfit(BigDecimal.valueOf(goal.getProductCoef() * totalTargetProfit.doubleValue()));
    }

    private Recommendation getRecommendation (Product product, Goal goal) {
        double dif =
                Maths.Newton.minimizePriceAdd(product.getProfit(), goal.getPrice().doubleValue(),
                                              goal.getCost().doubleValue(), goal.getSells(),
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
                (BigDecimal.valueOf(dif).multiply(
                        goal.getCost().divide(goal.getPrice(), RoundingMode.HALF_UP)))
                        .setScale(2, RoundingMode.CEILING)
                        .add(product.getProductCost());
        double sellsRecom = BigDecimal.valueOf(dif).multiply(
                        BigDecimal.valueOf(goal.getSells()).divide(goal.getPrice(), RoundingMode.HALF_UP))
                .add(BigDecimal.valueOf(product.getSells())).setScale(5,
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
                .product(product)
                .profitRecom(profitRecom)
                .deltaPrice(dif)
                .build();
    }

    private void roundingSells (List<Recommendation> recommendations,
                                List<Goal> goals) {
        recommendations = recommendations.parallelStream()
                .peek(this::roundSells)
                .collect(Collectors.toList());
    }

    private void roundSells (Recommendation rec) {
        Random randomForStream = new Random();
        randomForStream.setSeed(31 * System.nanoTime());
        if (randomForStream.nextBoolean()) {
            rec.setSellsRecom(
                    BigDecimal.valueOf(rec.getSellsRecom()).setScale(0,
                                                                     RoundingMode.UP).doubleValue());
        } else {
            rec.setSellsRecom(
                    BigDecimal.valueOf(rec.getSellsRecom()).setScale(0,
                                                                     RoundingMode.DOWN).doubleValue());
        }
        BigDecimal profitRecom =
                (rec.getProductPriceRecom()
                        .multiply(BigDecimal.valueOf(rec.getSellsRecom()))
                        .subtract(rec.getProductCostRecom()
                                          .multiply(BigDecimal.valueOf(rec.getSellsRecom())))
                ).setScale(2, RoundingMode.CEILING);
        rec.setProfitRecom(profitRecom);
    }

    private void minimizeRoundedProfits (List<Recommendation> recommendations, List<Goal> goals) {
        for (int i = 0; i < recommendations.size(); i++) {
            minimizeRoundedProfit(recommendations.get(i), goals.get(i));
        }
    }

    private void minimizeRoundedProfit (Recommendation rec, Goal goal) {
        double roundedDeltaPrice = Maths.Newton.minimizeRoundedProfit(0,
                                                                      rec.getSellsRecom().longValue(),
                                                                      rec.getProductPriceRecom().doubleValue(),
                                                                      rec.getProductCostRecom().doubleValue(),
                                                                      goal.getPrice().doubleValue(),
                                                                      goal.getCost().doubleValue(),
                                                                      goal.getProfit().doubleValue());
        BigDecimal recomPr = rec.getProductPriceRecom().add(BigDecimal.valueOf(roundedDeltaPrice));
        BigDecimal productPriceRecom = recomPr
                .setScale(2, RoundingMode.CEILING);
        BigDecimal productCostRecom =
                (BigDecimal.valueOf(roundedDeltaPrice).multiply(
                        goal.getCost().divide(goal.getPrice(), RoundingMode.HALF_UP)))
                        .setScale(2, RoundingMode.CEILING)
                        .add(rec.getProductCostRecom());
        BigDecimal profitRecom =
                (productPriceRecom
                        .multiply(BigDecimal.valueOf(rec.getSellsRecom()))
                        .subtract(productCostRecom
                                          .multiply(BigDecimal.valueOf(rec.getSellsRecom())))
                ).setScale(2, RoundingMode.CEILING);
        rec.setProductCostRecom(productCostRecom);
        rec.setProductPriceRecom(productPriceRecom);
        rec.setProfitRecom(profitRecom);
    }

}
