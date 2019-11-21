package com.toll.exercice.microservice;

import com.toll.exercice.microservice.dao.BillingDao;
import com.toll.exercice.microservice.models.Billing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;




@DataJpaTest
public class BillingDaoIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BillingDao billingDao;


    @Test
    public void whenFindByCarId_thenReturnBill() {
        Billing bill = new Billing(UUID.fromString("5d4992b6-0c54-11ea-8d71-362b9e155667"), LocalDateTime.now(), null);
        entityManager.persist(bill);
        entityManager.flush();

        // when
        Billing billRetreived = billingDao.findByCarId(UUID.fromString("5d4992b6-0c54-11ea-8d71-362b9e155667"));

        // then
        assertThat(billRetreived)
                .isNotNull();
        assertThat(billRetreived)
                .isEqualTo(bill);
    }

    @Test
    public void whenFindByCarIdWithWrong_thenReturnNull() {
        // when
        Billing bill = billingDao.findByCarId(UUID.fromString("9d4992b6-0c54-11ea-8d71-362b9e155667"));

        // then
        assertThat(bill)
                .isNull();
    }
}
