package com.toll.exercice.microservice.models;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;

@Entity
@EqualsAndHashCode
public class CarSlotOwner {
    @OneToOne
    protected PricePolicy pricePolicy;
    protected String name;
    @Id
    @GeneratedValue
    protected int id;

    public CarSlotOwner(PricePolicy pricePolicy, String name) {
        this.pricePolicy = pricePolicy;
        this.name = name;
    }

    public CarSlotOwner() {
    }

    public void setPricePolicy(PricePolicy pricePolicy)
    {
        this.pricePolicy = pricePolicy;
    }

    public  PricePolicy getPricePolicy()
    {
        return this.pricePolicy;
    }

}
