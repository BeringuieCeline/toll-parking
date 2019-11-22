package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.Billing;
import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.PricePolicy;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarSlotService {


    public Billing enter(CarSlot carSlot, String carType)
    {
        CarSlotTypeService carSlotTypeService = new CarSlotTypeService();
        if(!carSlotTypeService.isAcceptedCar(carSlot.getSlotType(), carType) || !isFree(carSlot)) {
            return null;
        }
        carSlot.setParkedAt(LocalDateTime.now());
        carSlot.setCarNumber(UUID.randomUUID());


        Billing bill = new Billing(carSlot.getCarNumber(), carSlot.getParkedAt(), null, -1.0f);
        carSlot.setBill(bill);
        return bill;
    }

    public Billing bill(CarSlot carSlot, UUID carNumber)
    {
        if(!isCorrectCar(carSlot, carNumber)) {
            return null;
        }
        BillingService billingService = new BillingService();
        billingService.closeIt(carSlot.getBill());
        PricePolicy pricePolicy = carSlot.getOwner().getPricePolicy();
        pricePolicy.fillBilling(carSlot.getBill());
        return carSlot.getBill();
    }

    public void leave(CarSlot carSlot)
    {
        carSlot.setParkedAt(null);
        carSlot.setCarNumber(null);
        carSlot.setBill(null);
    }

    public boolean isFree(CarSlot carSlot)
    {
        return carSlot.getBill() == null;
    }

    public boolean isCorrectCar(CarSlot carSlot, UUID carNumber)
    {
        return carSlot.getCarNumber().equals(carNumber);
    }
}
