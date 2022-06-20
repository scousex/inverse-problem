package asu.tusur.profitinverseproblem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGoalsWrapper {
    private List<Product> products;
    private List<Goal> goals;
    private BigDecimal profitGoal;
}
