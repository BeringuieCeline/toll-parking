package com.toll.exercice.microservice.models;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EqualsAndHashCode
public class CarSlot {
    @ManyToOne(cascade = CascadeType.ALL)
    protected CarSlotType slotType;
    @ManyToOne
    protected CarSlotOwner owner;
    protected LocalDateTime  parkedAt;
    protected UUID carNumber;
    @OneToOne(cascade = CascadeType.ALL)
    protected Billing bill;
    @Id
    @GeneratedValue
    protected int slotNumber;

    public CarSlot(CarSlotType slotType, CarSlotOwner owner, LocalDateTime parkedAt, UUID carNumber, Billing bill) {
        this.slotType = slotType;
        this.owner = owner;
        this.parkedAt = parkedAt;
        this.carNumber = carNumber;
        this.bill = bill;
    }

    public CarSlot() {
    }

    public Billing enter(String carType)
    {
        if(!slotType.isAcceptedCar(carType) || !isFree())
            return null;
        parkedAt = LocalDateTime.now();
        carNumber = UUID.randomUUID();


        bill = new Billing(carNumber, parkedAt, null);
        return bill;
    }

    public Billing bill(UUID carNumber)
    {
        if(!isCorrectCar(carNumber))
            return null;
        bill.closeIt();
        PricePolicy pricePolicy = owner.getPricePolicy();
        pricePolicy.fillBilling(bill);
        return bill;
    }

    public void leave()
    {
        if(!isCorrectCar(carNumber))
            return;
        parkedAt = null;
        carNumber = null;
        bill = null;
    }

     public boolean isFree()
     {
         return bill == null;
     }

     public boolean isCorrectCar(UUID carNumber)
     {
         return this.carNumber.equals(carNumber);
     }

    public CarSlotType getSlotType() {
        return slotType;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Billing getBill() {
        return bill;
    }
}
