package com.example.lostaf.lvaultweb.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.lostaf.lvaultweb.entities.Vault;
import com.example.lostaf.lvaultweb.repositories.custom.CustomRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;


public interface VaultRepository extends CustomRepository<Vault, UUID> {
    public List<Vault> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Vault v set v.name = ?1 where v.id = ?2")
    public Integer updateNameById(String name, UUID id);
}
