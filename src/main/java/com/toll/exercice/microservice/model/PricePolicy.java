/**
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class PricePolicy {
    @Id
    @GeneratedValue
    private int id;

    @ElementCollection
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private List<Float> variables;


    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String formula;

}
