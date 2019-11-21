package com.toll.exercice.microservice.models;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(exclude={"id"})
public class CarSlotType {
    protected String typeName;
    @Id
    @GeneratedValue
    protected int id;

    public CarSlotType() {
    }

    public CarSlotType(String typeName) {
        this.typeName = typeName;
    }


    public boolean isAcceptedCar(String carType)
    {
        return this.typeName.equals(carType);
    }


    public String getTypeName() {
        return typeName;
    }

}
