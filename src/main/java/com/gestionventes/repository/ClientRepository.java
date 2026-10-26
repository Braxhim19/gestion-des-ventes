package com.gestionventes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionventes.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNomContainingIgnoreCase(String nom);

    Client findByEmail(String email);

    Client findByTelephone(String telephone);
}