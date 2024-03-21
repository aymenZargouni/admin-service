package com.example.clientservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "contract")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contract {

    @Id
    private String id;
    @NonNull
    private ContractType contractType;
    private ContractType.PremiumType premiumType;
    private String entreprise;
    private String phoneNumber;
    private Date startDate;
    private Date endDate;
    private Date updateDate;
    private String description;
    private int maintenance;
    private int tickets;
    private int currentTickets;
}


