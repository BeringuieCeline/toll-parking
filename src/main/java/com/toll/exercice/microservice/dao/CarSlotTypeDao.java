/**
 * DAO class to access to CarSlotType object in base
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.model.CarSlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa repository to access and save CarSlotType object in base
 */
@Repository
public interface CarSlotTypeDao extends JpaRepository<CarSlotType, Integer> {
    CarSlotType findByTypeName(String typeName);
}
