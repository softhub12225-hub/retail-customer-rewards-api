package com.retail.rewards.config;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RewardsPeriodPropertiesTest {

    @Test
    void acceptsThreeCalendarMonthsInclusive() {
        RewardsPeriodProperties p = new RewardsPeriodProperties();
        p.setStartDate(LocalDate.of(2024, 1, 1));
        p.setEndDate(LocalDate.of(2024, 3, 31));
        assertThatCode(p::afterPropertiesSet).doesNotThrowAnyException();
    }

    @Test
    void rejectsShorterSpan() {
        RewardsPeriodProperties p = new RewardsPeriodProperties();
        p.setStartDate(LocalDate.of(2024, 1, 1));
        p.setEndDate(LocalDate.of(2024, 2, 28));
        assertThatThrownBy(p::afterPropertiesSet).isInstanceOf(IllegalStateException.class);
    }
}
