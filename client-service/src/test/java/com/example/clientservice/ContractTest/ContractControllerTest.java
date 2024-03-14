package com.example.clientservice.ContractTest;

import com.example.clientservice.controller.ContractController;
import com.example.clientservice.dto.ContractRequest;
import com.example.clientservice.dto.ContractResponse;
import com.example.clientservice.model.ContractType;
import com.example.clientservice.services.ContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContractController.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createClient_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ContractRequest request = ContractRequest.builder()
                .contractType(ContractType.STANDARD)
                .entreprise("New Company")
                .phoneNumber("12345689")
                .startDate(new Date(1970-01-01))
                .endDate(new Date(1970-01-01))
                .maintenance(3)
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(contractService).createContract(any(ContractRequest.class));
    }

    @Test
    public void createClient_InvalidData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ContractRequest request = new ContractRequest();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

        verify(contractService,never()).createContract(any(ContractRequest.class));
    }

    @Test
    public void editContract_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ContractRequest request = ContractRequest.builder()
                .contractType(ContractType.STANDARD)
                .entreprise("New Company")
                .phoneNumber("12345689")
                .startDate(new Date(1970-01-01))
                .endDate(new Date(1970-01-01))
                .maintenance(3)
                .build();

        String contractId = "1";
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/contract/" + contractId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        // Verify service method was called with correct parameters
        verify(contractService).editContract(any(ContractRequest.class), eq(contractId));
    }

    @Test
    public void editContract_FailedWithInvalidData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String contractId = "1";
        ContractRequest request = new ContractRequest();
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/contract/" + contractId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

        // Verify service method was called with correct parameters
        verify(contractService,never()).editContract(any(ContractRequest.class), eq(contractId));
    }

    @Test
    public void deleteContract_Success() throws Exception {

        String contractId = "1";

        mockMvc.perform(delete("/api/contract/delete/" + contractId))
                .andExpect(status().isOk());

        verify(contractService).deleteContract(contractId);
    }

    @Test
    public void getAllContracts_ReturnsData() throws Exception {
        List<ContractResponse> responses = List.of(new ContractResponse(), new ContractResponse());
        given(contractService.getAllContracts()).willReturn(responses);

        mockMvc.perform(get("/api/contract")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        verify(contractService).getAllContracts();
    }


}
