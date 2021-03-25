package my.company.project.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumericUtils {

    public static final int PRECISION_TWO_DIGIT = 2;
    //public static final MathContext MATH_CONTEXT_HALF_UP = new MathContext(PRECISION_TWO_DIGIT, RoundingMode.HALF_UP);
    //public static final MathContext MATH_CONTEXT_UP = new MathContext(PRECISION_TWO_DIGIT, RoundingMode.UP);
    public static final double ZERO = 0.0;

    public static BigDecimal getAmountWithTwoPrecisionAndRoundUp(double price) {
        BigDecimal value = new BigDecimal(price);
        value = value.setScale(PRECISION_TWO_DIGIT, RoundingMode.UP);
        return value;
    }

    public static BigDecimal getAmountWithTwoPrecisionAndRoundHalfUp(double price) {
        BigDecimal value = new BigDecimal(price);
        value = value.setScale(PRECISION_TWO_DIGIT, RoundingMode.HALF_UP);
        return value;

    }

    public static BigDecimal toBigDecimal(double price) {
        return new BigDecimal(price);
    }

    public static BigDecimal sum(BigDecimal a, double b) {
        BigDecimal bigDecimal = toBigDecimal(b);
        BigDecimal sum = a.add(bigDecimal);
        return sum;
    }

    public static BigDecimal sum(BigDecimal a, BigDecimal b) {
        a.add(b);
        return a;
    }

    public static BigDecimal subtraction(String minuend, double subtrahend) {
        double minuendDouble = new Double(0);
        try {
            minuendDouble = Double.parseDouble(minuend);
        } catch (Exception e) {
            return null;
        }
        return subtraction(minuendDouble, subtrahend);
    }

    public static BigDecimal subtraction(double minuend, double subtrahend) {
        BigDecimal minuendDec = new BigDecimal(minuend);
        BigDecimal subtrahendDec = new BigDecimal(subtrahend);

        minuendDec = minuendDec.subtract(subtrahendDec);

        return minuendDec;
    }

}
