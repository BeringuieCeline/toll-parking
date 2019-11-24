/**
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.temporal.ChronoUnit;

@Entity
@DiscriminatorValue("Fee")
public class FeeTimeBasePricePolicy extends PricePolicy {
    protected float hourPrice = 10.0f;
    protected float fee = 10.0f;

    @Override
    public void fillBilling(Bill bill)
    {
        bill.setPrice(fee+
                (bill.getParkedAt().until(bill.getLeaveAt(), ChronoUnit.HOURS)
                        + 1)
                        * hourPrice);
    }
}
