package com.toll.exercice.microservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CarSlot {
    @Id
    @GeneratedValue
    @Getter
    protected int slotNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter @Setter(AccessLevel.PROTECTED)
    protected CarSlotType slotType;
    @ManyToOne
    @Getter @Setter(AccessLevel.PROTECTED)
    protected CarSlotOwner owner;
    @Getter @Setter(AccessLevel.PUBLIC)
    protected LocalDateTime  parkedAt;
    @Getter @Setter(AccessLevel.PUBLIC)
    protected UUID carNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter(AccessLevel.PUBLIC)
    protected Billing bill;
}
