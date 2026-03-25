package com.retail.rewards.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Per transaction: 2 points per dollar over $100, plus 1 point per dollar between $50 and $100
 * (first $50 of the transaction earns 0 points).
 */
@Service
public class RewardPointsCalculator {

    private static final BigDecimal FIFTY = new BigDecimal("50");
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    public int pointsForTransaction(BigDecimal amountUsd) {
        if (amountUsd == null || amountUsd.signum() <= 0) {
            return 0;
        }
        BigDecimal amount = amountUsd.setScale(2, RoundingMode.HALF_UP);

        BigDecimal aboveHundred = amount.subtract(HUNDRED).max(BigDecimal.ZERO);
        BigDecimal band50to100 = amount.min(HUNDRED).subtract(FIFTY).max(BigDecimal.ZERO);

        int fromOver100 = aboveHundred.multiply(new BigDecimal("2")).setScale(0, RoundingMode.DOWN).intValueExact();
        int from50to100 = band50to100.setScale(0, RoundingMode.DOWN).intValueExact();

        return fromOver100 + from50to100;
    }
}
