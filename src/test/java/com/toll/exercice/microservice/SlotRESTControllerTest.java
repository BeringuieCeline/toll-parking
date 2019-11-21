package com.toll.exercice.microservice;


import com.toll.exercice.microservice.web.controller.SlotRESTController;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SlotRESTControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getCarSlotsAPI() throws Exception
    {
        /*mvc.perform( MockMvcRequestBuilders
                .get("v1:carSlots/{type}", "Gasoline")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.slotType").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.slotType.typeName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.slotType.typeName").value("Gasoline"));*/
    }

}
