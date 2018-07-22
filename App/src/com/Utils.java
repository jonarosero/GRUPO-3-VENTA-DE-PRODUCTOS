package com;

import java.math.BigDecimal;

/**
 *
 * @author bruno
 */
public class Utils {

    public static BigDecimal calculateCost(int itemQuantity, BigDecimal itemPrice) {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal itemCost = itemPrice.multiply(new BigDecimal(itemQuantity));
        totalCost = totalCost.add(itemCost);
        return totalCost;
    }
}
