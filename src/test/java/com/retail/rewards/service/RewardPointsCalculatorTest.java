package com.retail.rewards.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class RewardPointsCalculatorTest {

    private final RewardPointsCalculator calculator = new RewardPointsCalculator();

    @Test
    void exampleFromAssignment() {
        assertThat(calculator.pointsForTransaction(new BigDecimal("120.00"))).isEqualTo(90);
    }

    @Test
    void underFifty() {
        assertThat(calculator.pointsForTransaction(new BigDecimal("49.99"))).isZero();
    }

    @Test
    void exactlyFifty() {
        assertThat(calculator.pointsForTransaction(new BigDecimal("50.00"))).isZero();
    }

    @Test
    void exactlyHundred() {
        assertThat(calculator.pointsForTransaction(new BigDecimal("100.00"))).isEqualTo(50);
    }

    @Test
    void twoHundred() {
        assertThat(calculator.pointsForTransaction(new BigDecimal("200.00"))).isEqualTo(250);
    }
}
