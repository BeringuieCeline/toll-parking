/**
 * Service class with billing managing method
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.Bill;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Services to manage a bill. This class contain all the logic of a Bill and should be use for extrect orn modify data from <code>Bill</code>
 */
public class BillService {

    /**
     * Check the elapsed time since the bill was close. A bill, have a TTL of 5 minutes
     * @param bill the bill to check
     * @param now time to compare
     * @return true if elapsed time if under 5 minutes, false otherwise
     */
    public boolean isStillValid(Bill bill, LocalDateTime now) {
        return bill.getLeaveAt().until(now, ChronoUnit.MINUTES) <= 5;
    }

    /**
     * Close the bill by set the <code>leaveAt</code> time
     * @param bill the bill to close
     */
    public void closeIt(Bill bill) {
        bill.setLeaveAt(LocalDateTime.now());
    }
}
