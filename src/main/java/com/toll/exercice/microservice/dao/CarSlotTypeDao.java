package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.model.CarSlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSlotTypeDao extends JpaRepository<CarSlotType, Integer> {
    CarSlotType findByTypeName(String typeName);
}
