package com.example.clientservice.dto;

import com.example.clientservice.model.Contract;
import com.example.clientservice.model.ContractType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractRequest {

    @NonNull
    private ContractType contractType;
    private ContractType.PremiumType premiumType;
    private String entreprise;
    private String phoneNumber;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private int maintenance;
    private int tickets;


}
