/**
 * Service class with Car slot managing method
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.Bill;
import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.PricePolicy;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Services to manage a car slot. This class contain all the logic of a car slot and should be use for extrect orn modify data from <code>CarSlot</code>
 */
public class CarSlotService {

    /**
     * Flag the car slot <code>carSlot</code> as Not free by initializing the parking time and creating the bill
     * @param carSlot the CarSlot to flag
     * @param carType The type of the car that want to park here
     * @return the created bill object for this slot or null if <code>carType</code> doesn't match
     */
    public Bill enter(CarSlot carSlot, String carType)
    {
        CarSlotTypeService carSlotTypeService = new CarSlotTypeService();
        // if not supported car type or if slot is not free
        if(!carSlotTypeService.isAcceptedCar(carSlot.getSlotType(), carType) || !isFree(carSlot)) {
            return null;
        }

        carSlot.setParkedAt(LocalDateTime.now());
        carSlot.setCarNumber(UUID.randomUUID());

        Bill bill = new Bill(carSlot.getCarNumber(), carSlot.getParkedAt(), null, -1.0f);
        carSlot.setBill(bill);
        return bill;
    }

    /**
     * close thev billing with the current time for the car slot <code>carSlot</code>. update the price of the billing
     * @param carSlot the CarSlot to bill
     * @param carNumber car id to check if it's the correct car that ask for the billing
     * @return the final billing orb null if <code>carNumber</code> doesn't match
     */
    public Bill bill(CarSlot carSlot, UUID carNumber)
    {
        // if another car try to bo bill the car slot
        if(!isCorrectCar(carSlot, carNumber)) {
            return null;
        }
        BillService billService = new BillService();
        billService.closeIt(carSlot.getBill());
        PricePolicy pricePolicy = carSlot.getOwner().getPricePolicy();
        // complete billing with the Price Policy of the Owner
        pricePolicy.fillBilling(carSlot.getBill());
        return carSlot.getBill();
    }

    /**
     * Flag the slot <code>carSlot</code> as free by reset parked time, car id and bill at null
     * @param carSlot the CarSlot to leave
     */
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
