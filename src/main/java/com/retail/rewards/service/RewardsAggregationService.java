package com.retail.rewards.service;

import com.retail.rewards.model.PurchaseTransaction;
import com.retail.rewards.web.dto.CustomerRewardsDto;
import com.retail.rewards.web.dto.RewardsSummaryResponse;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RewardsAggregationService {

    private final RewardPointsCalculator calculator;
    private final RewardsPeriodValidator periodValidator;

    public RewardsAggregationService(
            RewardPointsCalculator calculator,
            RewardsPeriodValidator periodValidator) {
        this.calculator = calculator;
        this.periodValidator = periodValidator;
    }

    public RewardsSummaryResponse summarize(List<PurchaseTransaction> transactions) {
        periodValidator.assertAllWithinProgramPeriod(transactions);
        record Row(String customerId, YearMonth month, int points) {}

        List<Row> rows = new ArrayList<>();
        for (PurchaseTransaction t : transactions) {
            int pts = calculator.pointsForTransaction(t.amountUsd());
            YearMonth ym = YearMonth.from(t.purchaseDate());
            rows.add(new Row(t.customerId(), ym, pts));
        }

        Map<String, Map<YearMonth, Integer>> byCustomer = new TreeMap<>();
        for (Row r : rows) {
            byCustomer
                    .computeIfAbsent(r.customerId(), k -> new TreeMap<>())
                    .merge(r.month(), r.points(), Integer::sum);
        }

        List<CustomerRewardsDto> customers = new ArrayList<>();
        for (Map.Entry<String, Map<YearMonth, Integer>> e : byCustomer.entrySet()) {
            Map<String, Integer> byMonthStr = new LinkedHashMap<>();
            int total = 0;
            for (Map.Entry<YearMonth, Integer> me : e.getValue().entrySet()) {
                byMonthStr.put(me.getKey().toString(), me.getValue());
                total += me.getValue();
            }
            customers.add(new CustomerRewardsDto(e.getKey(), byMonthStr, total));
        }

        customers.sort(Comparator.comparing(CustomerRewardsDto::customerId));
        return new RewardsSummaryResponse(customers);
    }
}
