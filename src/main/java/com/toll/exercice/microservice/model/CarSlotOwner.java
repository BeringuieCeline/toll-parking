/**
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Class model for car slot Owner
 */
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
