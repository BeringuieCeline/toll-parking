/**
 * Service class with price policy managing method
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.Bill;
import com.toll.exercice.microservice.model.PricePolicy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.temporal.ChronoUnit;

/**
 * Services to manage a price policy. This class contain all the logic to apply a price policy to a <code>Bill</code>
 */
public class PricePolicyService {

    /**
     * fill bill byn applying the formula of the policy
     * @param pricePolicy policy to apply
     * @param bill bill to update
     */
    public void fillBill(PricePolicy pricePolicy, Bill bill) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        engine.put("variables", pricePolicy.getVariables());
        engine.put("hours", bill.getParkedAt().until(bill.getLeaveAt(), ChronoUnit.HOURS) + 1);
        try {
            bill.setPrice(Float.parseFloat(engine.eval(pricePolicy.getFormula()).toString()));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
