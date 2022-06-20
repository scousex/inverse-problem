package asu.tusur.profitinverseproblem.Model;

public class Maths {

    public static class ProfitFunction {

        /**
         * @param a - КОВ прироста цены
         * @param b - КОВ прироста себестоимости
         * @param c - КОВ прироста количества товара
         * @param K - кол-во товара
         * @param C - цена товара
         * @param S - себестоимость товара
         * @param x - прирост цены товара
         * @return прибыль от продажи кол-ва товара
         */
        public static Double calculate (Double a, Double b, Double c,
                                        Double K, Double C, Double S, Double x, Double deltaP) {
            return ((K * (x - ((b * x) / a))) + (((c * x) / a) * (C - S)) + (((c * x) / a) * (x - ((b * x) / a)))) - deltaP;
        }

        public static Double dif (Double a, Double b, Double c,
                                  Double K, Double C, Double S, Double x) {
            return ((((b / a) - 1) * (-(K + ((c * x) / a)))) - ((c * (S - x - C + ((b * x) / a))) / a));
        }

        public static Double dif2 (Double a, Double b, Double c) {
            return (2 * c / a) * (1 - b / a);
        }

        public static Double calculateRounding (long sells, Double deltaPrice, Double a, Double b,
                                                Double targetProfit) {
            return sells * (deltaPrice - (deltaPrice * (b / a))) - targetProfit;
        }

        public static Double difRounding (long sells, Double a, Double b) {
            return (-sells) * ((b / a) - 1);
        }
    }

    public static class Newton {
        private final static double e = 0.001;

        public static double minimizePriceAdd (double x0, Double a, Double b, Double c,
                                               Double K, Double C, Double S, Double deltaP) {
            double x1 = x0 - (ProfitFunction.calculate(a, b, c, K, C, S, x0, deltaP)
                    / ProfitFunction.dif(a, b, c, K, C, S, x0));
            double p = ProfitFunction.calculate(a, b, c, K, C, S, x0, deltaP);
            double dif = ProfitFunction.dif(a, b, c, K, C, S, x0);
            if (Math.abs(ProfitFunction.calculate(a, b, c, K, C, S, x0, deltaP)) < e) {
//                if(ProfitFunction.calculate(a,b,c,K,C,S,x1,deltaP)<0) return x0;
                return x1;
            } else return minimizePriceAdd(x1, a, b, c, K, C, S, deltaP);
        }

        public static double minimizeRoundedProfit (double x0, long sells, Double a,
                                                    Double b, Double targetProfit) {
            double x1 = x0 - (ProfitFunction.calculateRounding(sells, x0, a, b, targetProfit)
                    / ProfitFunction.difRounding(sells, a, b));
//            double p = ProfitFunction.calculate(a, b, c, K, C, S, x0, deltaP);
//            double dif = ProfitFunction.dif(a, b, c, K, C, S, x0);
            if (Math.abs(ProfitFunction.calculateRounding(sells, x0, a, b, targetProfit)) < e) {
//                if(ProfitFunction.calculate(a,b,c,K,C,S,x1,deltaP)<0) return x0;
                return x1;
            } else return minimizeRoundedProfit(x1, sells, a, b, targetProfit);
        }
    }

}
