package ru.kir.online.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void securityTokenTest(){
        String jsonRequest = "{\n" +
                "\t\"username\": \"admin\",\n" +
                "\t\"password\": \"100\"\n" +
                "}";

        try {
            MvcResult mvcResult = mockMvc.perform(post("/auth")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();
            String token = mvcResult.getResponse().getContentAsString();
            token = token.replace("{\"token\":\"", "").replace("\"}", "");

            mockMvc.perform(get("/api/v1/orders")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

