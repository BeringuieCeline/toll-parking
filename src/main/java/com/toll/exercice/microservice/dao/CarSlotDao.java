package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.model.CarSlot;
import com.toll.exercice.microservice.model.CarSlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarSlotDao extends JpaRepository<CarSlot, Integer> {

    CarSlot findBySlotNumber(int slotNumber);
    CarSlot findByCarNumber(UUID carNumber);

    List<CarSlot> findBySlotTypeAndCarNumberIsNullOrderBySlotNumberAsc(CarSlotType carSlotType);
}
