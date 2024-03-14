package com.example.clientservice.services;

import com.example.clientservice.dto.ContractRequest;
import com.example.clientservice.dto.ContractResponse;
import com.example.clientservice.model.Contract;
import com.example.clientservice.model.ContractType;
import com.example.clientservice.repo.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private ContractRepo contractRepo;

    public void createContract(ContractRequest contractRequest) {
        contractRequest.setTickets(0);
        Contract contract = Contract.builder()
                .contractType(contractRequest.getContractType())
                .premiumType(contractRequest.getPremiumType())
                .entreprise(contractRequest.getEntreprise())
                .phoneNumber(contractRequest.getPhoneNumber())
                .startDate(contractRequest.getStartDate())
                .endDate(contractRequest.getEndDate())
                .maintenance(contractRequest.getMaintenance())
                .build();
        switch (contractRequest.getContractType()){
            case STANDARD :
                contract.setPremiumType(null);
                break;
            case PREMIUM :
                switch (contractRequest.getPremiumType()){
                    case SILVER :
                        contract.setTickets(5);
                        break;
                    case GOLD:
                        contract.setTickets(10);
                        break;
                    case PLATINIUM:
                        contract.setTickets(15);
                        break;
                }
        }

        contractRepo.save(contract);
    }

    public List<ContractResponse> getAllContracts() {
        List<Contract> contracts = contractRepo.findAll();
        return contracts.stream().map(this::convertToContractResponse).toList();
    }

    private ContractResponse convertToContractResponse(Contract contract) {
        ContractResponse contractResponse = new ContractResponse();
        contractResponse.setId(contract.getId());
        contractResponse.setContractType(contract.getContractType());
        contractResponse.setPremiumType(contract.getPremiumType());
        contractResponse.setEntreprise(contract.getEntreprise());
        contractResponse.setPhoneNumber(contract.getPhoneNumber());
        contractResponse.setStartDate(contract.getStartDate());
        contractResponse.setEndDate(contract.getEndDate());
        contractResponse.setMaintenance(contract.getMaintenance());
        contractResponse.setTickets(contract.getTickets());
        return contractResponse;
    }

    public void editContract(ContractRequest contractRequest,String _id) {
        Optional<Contract> existingContract = contractRepo.findById(_id);
        if(existingContract.isPresent()){
            Contract contract = existingContract.get();

            contract.setContractType(contractRequest.getContractType());
            contract.setPremiumType(contractRequest.getPremiumType());
            contract.setEntreprise(contractRequest.getEntreprise());
            contract.setPhoneNumber(contractRequest.getPhoneNumber());
            contract.setStartDate(contractRequest.getStartDate());
            contract.setEndDate(contractRequest.getEndDate());
            contract.setMaintenance(contractRequest.getMaintenance());
            contract.setTickets(contractRequest.getTickets());

            contractRepo.save(contract);
        } else {
            throw new RuntimeException("Contract with ID " + _id + " not found");
        }
    }

    public void deleteContract(String id) {
        contractRepo.deleteById(id);
    }

    // ---------------------------------Repository methods----------------------------------
    //
    public Contract getContractById(String contractId){
        return contractRepo.getContractById(contractId);
    }

    public List<Contract>findByContractType(ContractType contractType){
        return contractRepo.findByContractType(contractType);
    }

    public List<Contract>findByPremiumType(ContractType.PremiumType premiumType){
        return contractRepo.findByPremiumType(premiumType);
    }

    public List<Contract>findByOrderByTicketsAsc(){
        return contractRepo.findByOrderByTicketsAsc();
    }

    public List<Contract>findByOrderByTicketsDesc(){
        return contractRepo.findByOrderByTicketsDesc();
    }
    /*
    public List<Contract>findByOrderByStartDate(){
        return contractRepo.findByOrderByStartDate();
    }
    */
    public List<Contract>findByOrderByEndDateAsc(){
        return contractRepo.findByOrderByEndDateAsc();
    }
    public List<Contract>findByOrderByEndDateDesc(){
        return contractRepo.findByOrderByEndDateDesc();
    }




}
