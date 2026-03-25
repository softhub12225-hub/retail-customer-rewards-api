package com.retail.rewards.web.dto;

import java.util.List;

public record RewardsSummaryResponse(
        List<CustomerRewardsDto> customers
) {
}
