package com.retail.rewards.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

/**
 * Inclusive reward program window. Must span exactly three calendar months (assignment requirement).
 */
@Validated
@ConfigurationProperties(prefix = "rewards.period")
public class RewardsPeriodProperties implements InitializingBean {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public void afterPropertiesSet() {
        if (endDate.isBefore(startDate)) {
            throw new IllegalStateException("rewards.period.end-date must be on or after rewards.period.start-date");
        }
        YearMonth start = YearMonth.from(startDate);
        YearMonth end = YearMonth.from(endDate);
        long inclusiveMonths = start.until(end, ChronoUnit.MONTHS) + 1;
        if (inclusiveMonths != 3) {
            throw new IllegalStateException(
                    "Reward program must cover exactly three calendar months; configured span is " + inclusiveMonths + " month(s)");
        }
    }
}
