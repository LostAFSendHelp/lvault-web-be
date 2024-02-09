package com.example.lostaf.lvaultweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lostaf.lvaultweb.entities.Vault;

public interface VaultRepository extends JpaRepository<Vault, Integer> {
    
}
