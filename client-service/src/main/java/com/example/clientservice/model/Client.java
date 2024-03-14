package com.example.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "client")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Client {

    @Id
    private String _id;
    private String email;
    private String password;
    @DBRef
    private List<Contract> contract;
    private int ticketsAvailable;

    public void updateTicketsAvailable() {
        int allTickets = this.getTicketsAvailable();
        this.ticketsAvailable = contract.stream().mapToInt(Contract::getTickets).sum()+allTickets;
    }
}
