package com.toll.exercice.microservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CarSlotOwner {
    @Id
    @GeneratedValue
    @Getter
    private int id;
    @OneToOne
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private PricePolicy pricePolicy;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String name;


}
