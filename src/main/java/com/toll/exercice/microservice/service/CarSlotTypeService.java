/**
 * Service class with type of slot managing method
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.service;

import com.toll.exercice.microservice.model.CarSlotType;

/**
 *
 */
public class CarSlotTypeService {

    public boolean isAcceptedCar(CarSlotType carSlotType, String carType)
    {
        return carSlotType.getTypeName().equals(carType);
    }
}
