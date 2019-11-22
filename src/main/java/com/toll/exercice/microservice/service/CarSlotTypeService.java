package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.CarSlotType;

public class CarSlotTypeService {

    public boolean isAcceptedCar(CarSlotType carSlotType, String carType)
    {
        return carSlotType.getTypeName().equals(carType);
    }
}
