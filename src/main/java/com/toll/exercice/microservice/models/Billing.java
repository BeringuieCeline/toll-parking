package com.toll.exercice.microservice.models;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@EqualsAndHashCode
public class Billing {
    @Id
    protected UUID carId;
    protected LocalDateTime parkedAt;
    protected LocalDateTime leaveAt;
    protected float price;

    public Billing()
    {

    }

    public Billing(UUID carId, LocalDateTime parkedAt, LocalDateTime leaveAt) {
        this.carId = carId;
        this.parkedAt = parkedAt;
        this.leaveAt = leaveAt;
        this.setPrice(-1.0f);
    }


    public UUID getCarId() {
        return carId;
    }

    public LocalDateTime getParkedAt() {
        return parkedAt;
    }

    public LocalDateTime getLeaveAt() {
        return leaveAt;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStillValid(LocalDateTime now) {
        return leaveAt.until(now, ChronoUnit.MINUTES) <= 5;
    }

    public void closeIt()
    {
        leaveAt = LocalDateTime.now();
    }
}
