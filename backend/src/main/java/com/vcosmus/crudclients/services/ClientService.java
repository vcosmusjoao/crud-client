package com.vcosmus.crudclients.services;

import com.vcosmus.crudclients.dto.ClientDTO;
import com.vcosmus.crudclients.entities.Client;
import com.vcosmus.crudclients.repositories.ClientRepository;
import com.vcosmus.crudclients.services.exception.DatabaseException;
import com.vcosmus.crudclients.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public ClientDTO findClientById(Long id) {
        Client entity = clientRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Não Encontrado"));
        ClientDTO clientDTO = new ClientDTO(entity);
        return clientDTO;
    }

    @Transactional
    public Page<ClientDTO> findAllClients(PageRequest pageRequest) {
        Page<Client> page = clientRepository.findAll(pageRequest);
         Page<ClientDTO> pageDTO = page.map(x-> new ClientDTO(x));
        return pageDTO;
    }

    @Transactional
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client entity = new Client();
        copyToEntity(entity,clientDTO);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        try{
            Client entity = clientRepository.getById(id);
            copyToEntity(entity,clientDTO);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id não encontrado "+id);
        }

    }

    public void deleteClient(Long id){
        try{
            clientRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id não encontrado "+id);
        }catch(DataIntegrityViolationException e){// caso meu cliente possua integridade com outras tabelas do banco, ou seja outras tabelas dependerem dele pra existir
            throw new DatabaseException("Violação de Integridade");
        }

    }

    private void copyToEntity(Client entity, ClientDTO clientDTO) {
        entity.setName(clientDTO.getName());
        entity.setCpf(clientDTO.getCpf());
        entity.setBirthDate(clientDTO.getBirthDate());
        entity.setIncome(clientDTO.getIncome());
        entity.setChildren(clientDTO.getChildren());
    }



}
