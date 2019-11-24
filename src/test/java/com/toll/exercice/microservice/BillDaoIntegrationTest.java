package com.toll.exercice.microservice;

import com.toll.exercice.microservice.dao.BillDao;
import com.toll.exercice.microservice.model.Bill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;




@DataJpaTest
public class BillDaoIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillDao billDao;


    @Test
    public void whenFindByCarId_thenReturnBill() {
        Bill bill = new Bill(UUID.fromString("5d4992b6-0c54-11ea-8d71-362b9e155667"), LocalDateTime.now(), null, -1.0f);
        entityManager.persist(bill);
        entityManager.flush();

        // when
        Bill billRetreived = billDao.findByCarId(UUID.fromString("5d4992b6-0c54-11ea-8d71-362b9e155667"));

        // then
        assertThat(billRetreived)
                .isNotNull();
        assertThat(billRetreived)
                .isEqualTo(bill);
    }

    @Test
    public void whenFindByCarIdWithWrong_thenReturnNull() {
        // when
        Bill bill = billDao.findByCarId(UUID.fromString("9d4992b6-0c54-11ea-8d71-362b9e155667"));

        // then
        assertThat(bill)
                .isNull();
    }
}
