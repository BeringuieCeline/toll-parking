package com.toll.exercice.microservice;


import com.toll.exercice.microservice.web.controller.SlotController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SlotControllerTest {

    @Autowired
    private MockMvc mvc;

    private final static String GASOLINE        =  "Gasoline";
    private final static String LOW_ELECTRICAL  =  "Low Powered electrical";
    private final static String HIGH_ELECTRICAL =  "High Powered electrical";
    private final static String FAKE_TYPE       =  "fakeTypeName";
    private final static String NO_SLOT_TYPE    =  "typeNoFreeSlot";

    private final static String GET_CAR_SLOTS   =  "/v1/carSlots/{type}";
    private final static String TYPE_NAME_PATH  =  "$.[*].slotType.typeName";
    private final static String PARKED_AT_PATH  =  "$.[*].parkedAt";
    private final static String CAR_NUMBER_PATH =  "$.[*].carNumber";
    private final static String BILL_PATH       =  "$.[*].bill";

    @Test
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

}
