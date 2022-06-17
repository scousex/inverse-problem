package asu.tusur.profitinverseproblem.Model;

import java.math.BigDecimal;

public class Goals {
    Double cost;
    Double price;
    Double sells;
    BigDecimal profit;

    public Goals(Double cost, Double price, Double sells, BigDecimal profit) {
        this.cost = cost;
        this.price = price;
        this.sells = sells;
        this.profit = profit;
    }

    public Goals() {
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSells() {
        return sells;
    }

    public void setSells(Double sells) {
        this.sells = sells;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
