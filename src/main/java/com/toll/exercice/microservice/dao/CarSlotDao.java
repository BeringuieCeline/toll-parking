/**
 * DAO class to access to CarSlot object in base
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.CarSlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


/**
 * Jpa repository to access and save CarSlot object in base
 */
@Repository
public interface CarSlotDao extends JpaRepository<CarSlot, Integer> {

    /**
     * find a car slot by his slot number
     * @param slotNumber the slot number to search for
     * @return the car slot refereed by <code>slotNumber</code>
     */
    CarSlot findBySlotNumber(int slotNumber);

    /**
     * find a car slot by the associated car number
     * @param carNumber the car id to searfch for
     * @return the associated car slot
     */
    CarSlot findByCarNumber(UUID carNumber);

    /**
     * find all free carSlots for a type, ordered by slot number
     * @param carSlotType the type to search for
     * @return array of free slot of type <code>carSlotType</code>
     */
    List<CarSlot> findBySlotTypeAndCarNumberIsNullOrderBySlotNumberAsc(CarSlotType carSlotType);
}
