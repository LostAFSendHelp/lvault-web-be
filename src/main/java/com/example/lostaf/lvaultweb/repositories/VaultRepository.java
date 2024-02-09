package com.example.lostaf.lvaultweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.lostaf.lvaultweb.entities.Vault;

public interface VaultRepository extends CrudRepository<Vault, Integer> {
    
}
