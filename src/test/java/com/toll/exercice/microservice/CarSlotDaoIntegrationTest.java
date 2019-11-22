package com.toll.exercice.microservice;

import com.toll.exercice.microservice.dao.CarSlotDao;
import com.toll.exercice.microservice.model.CarSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CarSlotDaoIntegrationTest {

    @Autowired
    private  TestEntityManager entityManager;

    @Autowired
    private CarSlotDao carSlotDao;


    @BeforeEach
    public void init()
    {
           }

    @Test
    public void whenFindBySlotNumber_thenReturnCarSlot() {
        // when
        CarSlot carSlot = carSlotDao.findBySlotNumber(1);

        // then
        assertThat(carSlot)
                .isNotNull();
        assertThat(carSlot.getSlotNumber())
                .isEqualTo(1);

    }

    @Test
    public void whenFindByTypeNameWrong_thenReturnNull() {
        // when

    }
}
