package com.example.clientservice.controller;

import com.example.clientservice.dto.ContractRequest;
import com.example.clientservice.dto.ContractResponse;
import com.example.clientservice.model.Contract;
import com.example.clientservice.model.ContractType;
import com.example.clientservice.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createContract (@RequestBody ContractRequest contractRequest){
        contractService.createContract(contractRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractResponse>getAllContracts(){
        return contractService.getAllContracts();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editContract (@RequestBody ContractRequest contractRequest,@PathVariable(name = "id")String _id){
        contractService.editContract(contractRequest,_id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContract (@PathVariable(name = "id")String _id){
        contractService.deleteContract(_id);
    }


    @GetMapping("get/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public Contract getContractById(@PathVariable(name = "contractId")String contractId){

        return contractService.getContractById(contractId);
    }

    @GetMapping("/getByContractType/{contractType}")
    @ResponseStatus(HttpStatus.OK)
    public List<Contract> getByContractType(@PathVariable(name = "contractType") ContractType contractType){
        return contractService.findByContractType(contractType);
    }

    @GetMapping("/getByPremiumType/{premiumType}")
    @ResponseStatus(HttpStatus.OK)
    public List<Contract> getByPremiumType(@PathVariable(name = "premiumType") ContractType.PremiumType premiumType){
        return contractService.findByPremiumType(premiumType);
    }
}
