package com.vcosmus.crudclients.controllers;

import com.vcosmus.crudclients.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.vcosmus.crudclients.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllClients(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                           @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                           @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                           @RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Page<ClientDTO> list = clientService.findAllClients(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id){
        ClientDTO clientDTO = clientService.findClientById(id);
        return ResponseEntity.ok().body(clientDTO);
    }


    @PostMapping
    public ResponseEntity<ClientDTO> addClient (@RequestBody ClientDTO clientDTO){
        clientDTO = clientService.addClient(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clientDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clientDTO);
    }
    


}
