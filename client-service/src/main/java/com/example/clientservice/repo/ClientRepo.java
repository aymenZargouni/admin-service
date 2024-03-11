package com.example.clientservice.repo;

import com.example.clientservice.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepo extends MongoRepository<Client,String> {
}
