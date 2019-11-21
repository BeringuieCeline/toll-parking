package com.toll.exercice.microservice.dao;

import com.toll.exercice.microservice.models.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingDao  extends JpaRepository<Billing, Integer> {
    Billing findByCarId(UUID id);
}
