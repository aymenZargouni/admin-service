package com.example.clientservice.services;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.dto.ContractRequest;
import com.example.clientservice.model.Client;
import com.example.clientservice.model.Contract;
import com.example.clientservice.repo.ClientRepo;
import com.example.clientservice.repo.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private ContractService contractService;

    public void createClient(ClientRequest clientRequest, String contractId) {
        Contract existingContract = contractRepo.getContractById(contractId);
        if (existingContract != null) {
            List<Contract> listContracts = new ArrayList<>();
            Client client = Client.builder()
                    .email(clientRequest.getEmail())
                    .password(clientRequest.getPassword())
                    .build();
            listContracts.add(existingContract);
            client.setContract(listContracts);

            clientRepo.save(client);
        } else {
            throw new RuntimeException("Contract with ID " + contractId + " not found");
        }
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        return clients.stream().map(this::convertToClientResponse).toList();
    }


    private ClientResponse convertToClientResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.set_id(client.get_id());
        clientResponse.setEmail(client.getEmail());
        clientResponse.setPassword(client.getPassword());
        clientResponse.setContract(client.getContract());
        return clientResponse;
    }

    public void addContractToClient(String clientId, String contractId) {
        Optional<Client> clientCheck = clientRepo.findById(clientId);
        if (!clientCheck.isPresent()) {
            throw new RuntimeException("Client with ID " + clientId + " not found");
        }
        Client existingClient = clientCheck.get();

        Contract existingContract = contractRepo.getContractById(contractId);
        if (existingContract == null) {
            throw new RuntimeException("Contract with ID " + contractId + " not found");
        }

        List<Contract> contractList = existingClient.getContract();
        if (contractList == null) {
            contractList = new ArrayList<>();
        }
        contractList.add(existingContract);
        existingClient.setContract(contractList);

        clientRepo.save(existingClient);
    }

    public void updateClientContracts(ClientRequest clientRequest, String clientId) {
        // Retrieve the client by ID
        Optional<Client> clientOptional = clientRepo.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new RuntimeException("Client with ID " + clientId + " not found");
        }
        Client client = clientOptional.get();

        // Extract the list of ContractRequest objects from the ClientRequest
        List<Contract> contractRequests = clientRequest.getContract();

        // Check if there are contracts provided in the request
        if (contractRequests == null || contractRequests.isEmpty()) {
            throw new IllegalArgumentException("No contract information provided in the request");
        }

        List<Contract> updatedContracts = new ArrayList<>();
        for (Contract cr : contractRequests) {
            updatedContracts.add(cr);
        }
        client.setContract(updatedContracts);
        clientRepo.save(client);

    }



    // Placeholder for the conversion method - you need to implement this based on your entity and DTO structure
    private Contract convertContractRequestToContract(ContractRequest cr) {
        Contract contract = new Contract();
        // Set properties from ContractRequest to Contract
        contract.setContractType(cr.getContractType());
        contract.setPremiumType(cr.getPremiumType());
        contract.setEntreprise(cr.getEntreprise());
        contract.setPhoneNumber(cr.getPhoneNumber());
        contract.setStartDate(cr.getStartDate());
        contract.setEndDate(cr.getEndDate());
        contract.setMaintenance(cr.getMaintenance());
        contract.setTickets(cr.getTickets());
        // Add more fields as necessary

        return contract;
    }


    public List<Contract> getClientContracts(String clientId){
        Optional<Client> clientOptional = clientRepo.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new RuntimeException("Client with ID " + clientId + " not found");
        }
        Client client = clientOptional.get();
        return client.getContract();
    }

    private ContractRequest convertToContractRequest(Contract contract) {

        ContractRequest contractRequest = new ContractRequest();
        contractRequest.setContractType(contract.getContractType());
        contractRequest.setPremiumType(contract.getPremiumType());
        contractRequest.setEntreprise(contract.getEntreprise());
        contractRequest.setPhoneNumber(contract.getPhoneNumber());
        contractRequest.setStartDate(contract.getStartDate());
        contractRequest.setEndDate(contract.getEndDate());
        contractRequest.setMaintenance(contract.getMaintenance());
        contractRequest.setTickets(contract.getTickets());


        return contractRequest;
    }

}