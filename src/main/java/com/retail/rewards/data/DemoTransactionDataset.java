package com.retail.rewards.data;

import com.retail.rewards.model.PurchaseTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Three-month window (Jan–Mar 2024) with multiple customers illustrating each earning band.
 */
public final class DemoTransactionDataset {

    private DemoTransactionDataset() {
    }

    public static List<PurchaseTransaction> all() {
        return List.of(
                // Assignment example: $120 → 90 points
                new PurchaseTransaction("alice", new BigDecimal("120.00"), LocalDate.of(2024, 1, 15)),
                // Under $50 → 0
                new PurchaseTransaction("bob", new BigDecimal("45.00"), LocalDate.of(2024, 1, 8)),
                // $50–$100 band only: $75 → 25 points
                new PurchaseTransaction("bob", new BigDecimal("75.00"), LocalDate.of(2024, 2, 3)),
                // Exactly $100 → 50 points in middle band
                new PurchaseTransaction("charlie", new BigDecimal("100.00"), LocalDate.of(2024, 1, 22)),
                // Over $100: $200 → 50 + 200 = 250
                new PurchaseTransaction("charlie", new BigDecimal("200.00"), LocalDate.of(2024, 3, 10)),
                // Second month for alice: $99 → 49 points
                new PurchaseTransaction("alice", new BigDecimal("99.00"), LocalDate.of(2024, 2, 20)),
                // Edge: just above $50
                new PurchaseTransaction("diana", new BigDecimal("50.01"), LocalDate.of(2024, 3, 5)),
                // Same customer, two purchases same month
                new PurchaseTransaction("diana", new BigDecimal("130.00"), LocalDate.of(2024, 3, 28))
        );
    }
}
