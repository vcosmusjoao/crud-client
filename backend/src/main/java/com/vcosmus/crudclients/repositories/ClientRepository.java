package com.vcosmus.crudclients.repositories;

import com.vcosmus.crudclients.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
