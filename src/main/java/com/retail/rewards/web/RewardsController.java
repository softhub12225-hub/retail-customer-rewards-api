package com.retail.rewards.web;

import com.retail.rewards.data.DemoTransactionDataset;
import com.retail.rewards.model.PurchaseTransaction;
import com.retail.rewards.service.RewardsAggregationService;
import com.retail.rewards.web.dto.CalculateRewardsRequest;
import com.retail.rewards.web.dto.RewardsSummaryResponse;
import com.retail.rewards.web.dto.TransactionRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
public class RewardsController {

    private final RewardsAggregationService aggregationService;

    public RewardsController(RewardsAggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    /**
     * Pre-built dataset (three months, multiple customers, all earning bands).
     */
    @GetMapping("/demo")
    public RewardsSummaryResponse demo() {
        return aggregationService.summarize(DemoTransactionDataset.all());
    }

    /**
     * Calculate rewards for an arbitrary list of purchase transactions.
     */
    @PostMapping(path = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RewardsSummaryResponse calculate(@Valid @RequestBody CalculateRewardsRequest body) {
        List<PurchaseTransaction> list = body.transactions().stream()
                .map(RewardsController::toModel)
                .toList();
        return aggregationService.summarize(list);
    }

    private static PurchaseTransaction toModel(TransactionRequest r) {
        return new PurchaseTransaction(r.customerId(), r.amountUsd(), r.purchaseDate());
    }
}
