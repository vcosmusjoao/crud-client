package com.vcosmus.crudclients.services;

import com.vcosmus.crudclients.dto.ClientDTO;
import com.vcosmus.crudclients.entities.Client;
import com.vcosmus.crudclients.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client entity = new Client();
        copyToEntity(entity,clientDTO);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    private void copyToEntity(Client entity, ClientDTO clientDTO) {
        entity.setName(clientDTO.getName());
        entity.setCpf(clientDTO.getCpf());
        entity.setBirthDate(clientDTO.getBirthDate());
        entity.setIncome(clientDTO.getIncome());
        entity.setChildren(clientDTO.getChildren());
    }
}
