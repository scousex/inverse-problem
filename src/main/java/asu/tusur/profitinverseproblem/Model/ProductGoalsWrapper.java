package asu.tusur.profitinverseproblem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGoalsWrapper {
    private ArrayList<Product> products;
    private ArrayList<Goals> goals;
    private BigDecimal profitGoal;
}
