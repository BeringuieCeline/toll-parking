package com.toll.exercice.microservice.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CarSlotTest {

    private static CarSlot carSlot;
    @BeforeAll
    public static void setup()
    {
        carSlot = new CarSlot(new CarSlotType("GazTest"), new CarSlotOwner(new NoFeeTimeBasePricePolicy(), "person"), null, null, null);
    }

    @Test
    public void testEnter() {
        // error case
        Billing bill = carSlot.enter("BadType");

        assertThat(bill)
                .isNull();

        // base test
        bill = carSlot.enter("GazTest");

        assertThat(bill)
                .isNotNull();
    }

    @Test
    void bill() {
    }

    @Test
    void leave() {
    }

    @Test
    void isFree() {
    }

    @Test
    void isCorrectCar() {
    }
}