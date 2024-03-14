package com.example.clientservice.dto;

import com.example.clientservice.model.Contract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "client")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientResponse {

    private String _id;
    private String email;
    private String password;
    private List<Contract> contract;
    private int ticketsAvailable;

}
