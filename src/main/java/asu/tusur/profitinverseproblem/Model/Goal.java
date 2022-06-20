package asu.tusur.profitinverseproblem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Goal {
    private Double productCoef;
    private BigDecimal cost;
    private BigDecimal price;
    private Double sells;
    private BigDecimal profit;
}
