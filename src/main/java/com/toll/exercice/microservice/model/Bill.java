/**
 *
 * @author  Djoé DENNE
 * @version 1.0
 * @since   2019-11-23
 */
package com.toll.exercice.microservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class model for car slot bill
 */
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @Getter
    private UUID carId;
    @Getter @Setter(AccessLevel.PROTECTED)
    private LocalDateTime parkedAt;
    @Getter @Setter(AccessLevel.PUBLIC)
    private LocalDateTime leaveAt;
    @Getter @Setter
    private float price;

}
