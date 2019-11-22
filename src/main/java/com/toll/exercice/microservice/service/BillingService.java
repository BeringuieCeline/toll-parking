package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.Billing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BillingService {

    public boolean isStillValid(Billing bill, LocalDateTime now) {
        return bill.getLeaveAt().until(now, ChronoUnit.MINUTES) <= 5;
    }

    public void closeIt(Billing bill) {
        bill.setLeaveAt(LocalDateTime.now());
    }
}
