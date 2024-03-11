package com.example.clientservice.repo;

import com.example.clientservice.model.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContractRepo extends MongoRepository<Contract,String> {

    Contract getContractById(String contractId);
}
