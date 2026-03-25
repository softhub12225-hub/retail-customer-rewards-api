package com.retail.rewards.web.dto;

import java.util.Map;

public record CustomerRewardsDto(
        String customerId,
        Map<String, Integer> pointsByMonth,
        int totalPoints
) {
}
