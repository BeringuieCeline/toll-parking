package com.toll.exercice.microservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
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
