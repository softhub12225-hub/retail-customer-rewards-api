package com.retail.rewards.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.rewards.web.dto.CalculateRewardsRequest;
import com.retail.rewards.web.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void demoReturnsCustomers() throws Exception {
        mockMvc.perform(get("/api/rewards/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers.length()").value(4))
                .andExpect(jsonPath("$.customers[0].customerId").value("alice"))
                .andExpect(jsonPath("$.customers[0].totalPoints").value(139));
    }

    @Test
    void calculateAcceptsPayload() throws Exception {
        var body = new CalculateRewardsRequest(List.of(
                new TransactionRequest("x", new BigDecimal("120"), LocalDate.of(2024, 1, 1))
        ));
        mockMvc.perform(post("/api/rewards/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers[0].customerId").value("x"))
                .andExpect(jsonPath("$.customers[0].totalPoints").value(90));
    }
}
