package asu.tusur.profitinverseproblem.Model;

import asu.tusur.profitinverseproblem.exceptions.CalculateException;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.abs;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@Data
public class Recommendation {
    private BigDecimal productCostRecom;
    private BigDecimal productPriceRecom;
    private Double sellsRecom;
    private BigDecimal profitRecom;
    private long productId;
}
