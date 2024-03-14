package com.example.clientservice.ContractTest;

import com.example.clientservice.dto.ContractRequest;
import com.example.clientservice.dto.ContractResponse;
import com.example.clientservice.model.Contract;
import com.example.clientservice.model.ContractType;
import com.example.clientservice.repo.ContractRepo;
import com.example.clientservice.services.ContractService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContractServiceTest {

    @Mock
    private ContractRepo contractRepo;

    @InjectMocks
    private ContractService contractService;

    @Captor
    private ArgumentCaptor<Contract> contractCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createContract_standardType_setsPremiumTypeToNull() {
        ContractRequest request = ContractRequest.builder()
                .contractType(ContractType.STANDARD)
                .entreprise("TESTENTREPRISE")
                .phoneNumber("12345689")
                .startDate(new Date(2024-03-07))
                .endDate(new Date(2025-03-07))
                .maintenance(3)
                .build();

        contractService.createContract(request);

        verify(contractRepo).save(contractCaptor.capture());
        Contract savedContract = contractCaptor.getValue();

        assertNull(savedContract.getPremiumType(), "PremiumType should be null for STANDARD contracts");
    }

    @Test
    public void createContract_premiumTypeSilver_setsTicketsTo5() {
        ContractRequest request = ContractRequest.builder()
                .contractType(ContractType.PREMIUM)
                .premiumType(ContractType.PremiumType.SILVER)
                .entreprise("TESTENTREPRISE")
                .phoneNumber("12345689")
                .startDate(new Date(2024-03-07))
                .endDate(new Date(2025-03-07))
                .maintenance(3)
                .build();

        contractService.createContract(request);

        verify(contractRepo).save(contractCaptor.capture());
        Contract savedContract = contractCaptor.getValue();

        assertEquals(5, savedContract.getTickets());
    }

    @Test
    public void createContract_premiumTypeGold_setsTicketsTo10() {
        ContractRequest request = ContractRequest.builder()
                .contractType(ContractType.PREMIUM)
                .premiumType(ContractType.PremiumType.GOLD)
                .entreprise("TESTENTREPRISE")
                .phoneNumber("12345689")
                .startDate(new Date(2024-03-07))
                .endDate(new Date(2025-03-07))
                .maintenance(3)
                .build();

        contractService.createContract(request);

        verify(contractRepo).save(contractCaptor.capture());
        Contract savedContract = contractCaptor.getValue();

        assertEquals(10, savedContract.getTickets());
    }

    @Test
    public void getAllContracts_returnsEmptyListWhenNoContractsAvailable() {
        when(contractRepo.findAll()).thenReturn(Collections.emptyList());

        List<ContractResponse> responses = contractService.getAllContracts();

        assertTrue(responses.isEmpty());
    }

    @Test
    public void getAllContracts_Success() {
        // Setup mock contracts
        Contract contract = Contract.builder()
                .id("1")
                .contractType(ContractType.PREMIUM)
                .premiumType(ContractType.PremiumType.GOLD)
                .entreprise("TESTENTREPRISE")
                .phoneNumber("12345689")
                .startDate(new Date(2024-03-07))
                .endDate(new Date(2025-03-07))
                .maintenance(3)
                .build();


        when(contractRepo.findAll()).thenReturn(Arrays.asList(contract));

        List<ContractResponse> responses = contractService.getAllContracts();

        assertEquals(1, responses.size());
        assertEquals("1", responses.get(0).getId());
    }

}
