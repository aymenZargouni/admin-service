package com.example.clientservice.dto;

import com.example.clientservice.model.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractResponse {

    private String id;
    private ContractType contractType;
    private ContractType.PremiumType premiumType;
    private String entreprise;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
    private int maintenance;
    private int tickets;
}
