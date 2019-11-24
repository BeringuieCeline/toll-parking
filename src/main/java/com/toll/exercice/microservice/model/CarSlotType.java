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

/**
 * Class model for car slot Type
 */
@Entity
@EqualsAndHashCode(exclude={"id"})
@NoArgsConstructor
@AllArgsConstructor
public class CarSlotType {
    @Id
    @GeneratedValue
    @Getter
    protected int id;
    @Getter
    @Setter(AccessLevel.PROTECTED)
    protected String typeName;

}
