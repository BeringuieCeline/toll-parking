/**
 * DAO class to access to Bill object in base
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Jpa repository to access and save Bill object in base
 */
@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {
    /**
     * find a bill by the associated car id
     * @param id car id to search for
     * @return the associated Bill
     */
    Bill findByCarId(UUID id);
}
