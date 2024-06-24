package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.ClientDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Client;
import org.reseaux.carLoc.models.User;
import org.reseaux.carLoc.repositories.ClientRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findOne(long clientId) {
        return clientRepository.findById(clientId);
    }

    public Client create(ClientDTO clientDTO) {
        Client client = new Client();
        Long newId = cassandraIdGenerator.getNextId("clients");
        client.setId(newId);
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        client.setSexe(clientDTO.getSexe());
        client.setPhone(clientDTO.getPhone());
        return clientRepository.save(client);
    }

    public Client update(long clientId, ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setSexe(clientDTO.getSexe());
            client.setPhone(clientDTO.getPhone());
            return clientRepository.save(client);
        } else {
            throw new ResourceNotFoundException("Client not found with id " + clientId);
        }
    }

    public void delete(long clientId) {
        clientRepository.deleteById(clientId);
    }

    public Optional<Client> login(ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository.findByEmail(clientDTO.getEmail());
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            if(client.getPassword().equals(clientDTO.getPassword())){
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }
}
