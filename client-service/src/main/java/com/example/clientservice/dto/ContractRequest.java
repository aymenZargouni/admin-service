package com.example.clientservice.dto;

import com.example.clientservice.model.Contract;
import com.example.clientservice.model.ContractType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractRequest {

    private ContractType contractType;
    private ContractType.PremiumType premiumType;
    private String entreprise;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
    private int maintenance;
    private int tickets;


}
