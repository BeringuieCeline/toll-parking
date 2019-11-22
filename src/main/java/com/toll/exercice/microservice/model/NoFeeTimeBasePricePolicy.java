package com.toll.exercice.microservice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.temporal.ChronoUnit;

@Entity
@DiscriminatorValue("NoFee")
public class NoFeeTimeBasePricePolicy extends PricePolicy {
    protected float hourPrice = 10.0f;

    @Override
    public void fillBilling(Billing billing)
    {
        billing.setPrice(
                (billing.getParkedAt().until(billing.getLeaveAt(), ChronoUnit.HOURS)
                 + 1)
                 * hourPrice);
    }
}
