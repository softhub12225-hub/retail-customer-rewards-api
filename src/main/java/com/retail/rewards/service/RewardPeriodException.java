package com.retail.rewards.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RewardPeriodException extends RuntimeException {

    public RewardPeriodException(String message) {
        super(message);
    }
}
