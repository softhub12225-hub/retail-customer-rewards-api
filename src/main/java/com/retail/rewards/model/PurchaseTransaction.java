package com.retail.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseTransaction(
        String customerId,
        BigDecimal amountUsd,
        LocalDate purchaseDate
) {
}
