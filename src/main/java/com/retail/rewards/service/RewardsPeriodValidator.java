package com.retail.rewards.service;

import com.retail.rewards.config.RewardsPeriodProperties;
import com.retail.rewards.model.PurchaseTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RewardsPeriodValidator {

    private final RewardsPeriodProperties period;

    public RewardsPeriodValidator(RewardsPeriodProperties period) {
        this.period = period;
    }

    /**
     * Ensures every purchase falls within the configured three-month program window (inclusive).
     */
    public void assertAllWithinProgramPeriod(List<PurchaseTransaction> transactions) {
        LocalDate start = period.getStartDate();
        LocalDate end = period.getEndDate();
        for (PurchaseTransaction t : transactions) {
            LocalDate d = t.purchaseDate();
            if (d.isBefore(start) || d.isAfter(end)) {
                throw new RewardPeriodException(
                        "Purchase date " + d + " is outside the reward program period " + start + " to " + end + " (inclusive).");
            }
        }
    }
}
