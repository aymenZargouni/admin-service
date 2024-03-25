package com.example.clientservice.repo;

import com.example.clientservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepo extends MongoRepository<Client,String> {
    Optional<Client> findClientById(String clientId);
}
