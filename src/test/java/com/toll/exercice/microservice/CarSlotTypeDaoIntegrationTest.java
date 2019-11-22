package com.toll.exercice.microservice;

import com.toll.exercice.microservice.dao.CarSlotTypeDao;
import com.toll.exercice.microservice.model.CarSlotType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class CarSlotTypeDaoIntegrationTest {
    @Autowired
    private CarSlotTypeDao carSlotTypeDao;

    @Test
    public void whenFindByTypeName_thenReturnCarSlotType() {
        // when
        CarSlotType carSlotType = carSlotTypeDao.findByTypeName("Gasoline");

        // then
        assertThat(carSlotType)
                .isNotNull();
        assertThat(carSlotType.getTypeName())
                .isEqualTo("Gasoline");

        // when
        carSlotType = carSlotTypeDao.findByTypeName("Low Powered electrical");

        // then
        assertThat(carSlotType)
                .isNotNull();
        assertThat(carSlotType.getTypeName())
                .isEqualTo("Low Powered electrical");

        // when
        carSlotType = carSlotTypeDao.findByTypeName("High Powered electrical");

        // then
        assertThat(carSlotType)
                .isNotNull();
        assertThat(carSlotType.getTypeName())
                .isEqualTo("High Powered electrical");

        // when
        carSlotType = carSlotTypeDao.findByTypeName("Low Powered electrical");

        // then
        assertThat(carSlotType)
                .isNotNull();
        assertThat(carSlotType.getTypeName())
                .isEqualTo("Low Powered electrical");
    }

    @Test
    public void whenFindByTypeNameWrong_thenReturnNull() {
        // when
        CarSlotType carSlotType = carSlotTypeDao.findByTypeName("Gas");

        // then
        assertThat(carSlotType)
                .isNull();
    }
}
