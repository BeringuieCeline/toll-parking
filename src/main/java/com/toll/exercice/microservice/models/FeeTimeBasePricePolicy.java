package com.toll.exercice.microservice.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Entity
@DiscriminatorValue("Fee")
public class FeeTimeBasePricePolicy extends PricePolicy {
    protected float hourPrice = 10.0f;
    protected float fee = 10.0f;

    @Override
    public void fillBilling(Billing billing)
    {
        billing.setPrice(fee+
                (billing.getParkedAt().until(billing.getLeaveAt(), ChronoUnit.HOURS)
                        + 1)
                        * hourPrice);
    }
}
