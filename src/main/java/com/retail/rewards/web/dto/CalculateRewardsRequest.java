package com.retail.rewards.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CalculateRewardsRequest(
        @NotEmpty @Valid List<TransactionRequest> transactions
) {
}
