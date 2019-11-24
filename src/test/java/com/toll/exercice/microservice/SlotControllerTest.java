package com.toll.exercice.microservice;


import com.toll.exercice.microservice.dao.CarSlotDao;
import com.toll.exercice.microservice.model.Bill;
import com.toll.exercice.microservice.model.CarSlot;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SlotControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarSlotDao carSlotDao;

    private final static String GASOLINE        =  "Gasoline";
    private final static String LOW_ELECTRICAL  =  "Low Powered electrical";
    private final static String HIGH_ELECTRICAL =  "High Powered electrical";
    private final static String FAKE_TYPE       =  "fakeTypeName";
    private final static String NO_SLOT_TYPE    =  "typeNoFreeSlot";

    private final static String GET_CAR_SLOTS   =  "/v1/carSlots/{type}";
    private final static String POST_CAR_SLOTS  =  "/v1/car/{id}/{type}";
    private final static String GET_BILL        =  "/v1/bill/{uuid}";
    private final static String DELETE_CAR_SLOTS=  "/v1/car/{uuid}";


    private final static String ROOT            =  "$";
    private final static String TYPE_NAME_PATH  =  "$.[*].slotType.typeName";
    private final static String PARKED_AT_PATH  =  "$.[*].parkedAt";
    private final static String CAR_NUMBER_PATH =  "$.[*].carNumber";
    private final static String BILL_PATH       =  "$.[*].bill";

    private final static String BILL_CAR_ID_PATH    =  "$.carId";
    private final static String BILL_PARKED_AT_PATH =  "$.parkedAt";
    private final static String BILL_LEAVE_AT_PATH  =  "$.leaveAt";
    private final static String BILL_PRICE_PATH     =  "$.price";

    private static UUID gasolineCar;
    private static UUID lowElectricalCar;
    private static UUID highElectricalCar;

    @Test
    @Order(1)
    public void getCarSlotsAPI() throws Exception {
        // test all base case
        testGatCarSlotsForSpecificType(GASOLINE);
        testGatCarSlotsForSpecificType(LOW_ELECTRICAL);
        testGatCarSlotsForSpecificType(HIGH_ELECTRICAL);

        // test not correct type
        mvc.perform( MockMvcRequestBuilders
                .get(GET_CAR_SLOTS, FAKE_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // test with no free slot
        mvc.perform( MockMvcRequestBuilders
                .get(GET_CAR_SLOTS, NO_SLOT_TYPE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    private void testGatCarSlotsForSpecificType(String typeName) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get(GET_CAR_SLOTS, typeName)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(TYPE_NAME_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(TYPE_NAME_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(TYPE_NAME_PATH).isArray())
                .andExpect(MockMvcResultMatchers.jsonPath(TYPE_NAME_PATH, hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath(TYPE_NAME_PATH, everyItem(equalTo(typeName))))
                .andExpect(MockMvcResultMatchers.jsonPath(PARKED_AT_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(PARKED_AT_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(PARKED_AT_PATH).isArray())
                .andExpect(MockMvcResultMatchers.jsonPath(PARKED_AT_PATH, hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath(PARKED_AT_PATH, everyItem(equalTo(null))))
                .andExpect(MockMvcResultMatchers.jsonPath(CAR_NUMBER_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(CAR_NUMBER_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(CAR_NUMBER_PATH).isArray())
                .andExpect(MockMvcResultMatchers.jsonPath(CAR_NUMBER_PATH, hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath(CAR_NUMBER_PATH, everyItem(equalTo(null))))
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PATH).isArray())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PATH, hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PATH, everyItem(equalTo(null))));
    }

    @Test
    @Order(2)
    public void postParkCarSlotAPI() throws Exception {
        // test all base case
        gasolineCar = testPostCar(1, GASOLINE);
        lowElectricalCar = testPostCar(2, LOW_ELECTRICAL);
        highElectricalCar = testPostCar(3, HIGH_ELECTRICAL);

        // With wrong slot number
        mvc.perform( MockMvcRequestBuilders
                .post(POST_CAR_SLOTS, 666, GASOLINE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // With wrong slot number
        mvc.perform( MockMvcRequestBuilders
                .post(POST_CAR_SLOTS, 666, GASOLINE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // With wrong type
        mvc.perform( MockMvcRequestBuilders
                .post(POST_CAR_SLOTS, 4, LOW_ELECTRICAL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // With not free slot
        mvc.perform( MockMvcRequestBuilders
                .post(POST_CAR_SLOTS, 1, GASOLINE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private UUID testPostCar(int carSlotNumber, String type) throws Exception
    {
        LocalDateTime now = LocalDateTime.now();
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post(POST_CAR_SLOTS, carSlotNumber, type)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(ROOT).value(Matchers.matchesRegex("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")))
                .andReturn();

        String uuid = result.getResponse().getContentAsString().replaceAll("\"", "");
        UUID carId = UUID.fromString(uuid);

        CarSlot carSlot = carSlotDao.findByCarNumber(carId);

        assertThat(carSlot)
                .isNotNull();
        assertThat(carSlot.getSlotNumber())
                .isEqualTo(carSlotNumber);

        LocalDateTime parkedAt = carSlot.getParkedAt();
        assertThat(parkedAt)
                .isNotNull();
        assertThat(parkedAt)
                .isAfterOrEqualTo(now)
                .isBeforeOrEqualTo(now.plusSeconds(1));

        Bill bill = carSlot.getBill();
        assertThat(bill)
                .isNotNull();

        assertThat(bill.getParkedAt())
                .isEqualTo(parkedAt);

        assertThat(bill.getLeaveAt())
                .isNull();

        assertThat(bill.getCarId())
                .isEqualTo(carId);

        assertThat(bill.getPrice())
                .isEqualTo(-1.0f);

        return carId;
    }

    @Test
    @Order(3)
    public void getBillAPI() throws Exception {
        testGetBill(gasolineCar);
        testGetBill(lowElectricalCar);
        testGetBill(highElectricalCar);
    }

    private void testGetBill(UUID carId) throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                .get(GET_BILL, carId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_CAR_ID_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_CAR_ID_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_CAR_ID_PATH).value(Matchers.matchesRegex("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")))
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PARKED_AT_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PARKED_AT_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PARKED_AT_PATH).value(Matchers.matchesRegex("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d*$")))
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_LEAVE_AT_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_LEAVE_AT_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_LEAVE_AT_PATH).value(Matchers.matchesRegex("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d*$")))
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PRICE_PATH).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PRICE_PATH).isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath(BILL_PRICE_PATH).value(10.0F))
                .andReturn();
    }


    @Test
    @Order(4)
    public void deleteCarAPI() throws Exception {
        testDeleteCar(gasolineCar, 1);
        testDeleteCar(lowElectricalCar, 2);
        testDeleteCar(highElectricalCar, 3);
    }

    private void testDeleteCar(UUID carId, int id) throws Exception{
        mvc.perform( MockMvcRequestBuilders
                .delete(DELETE_CAR_SLOTS, carId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

        CarSlot carSlot = carSlotDao.findBySlotNumber(id);

        assertThat(carSlot.getCarNumber())
                .isNull();
        assertThat(carSlot.getParkedAt())
                .isNull();
        assertThat(carSlot.getBill())
                .isNull();
    }

}
