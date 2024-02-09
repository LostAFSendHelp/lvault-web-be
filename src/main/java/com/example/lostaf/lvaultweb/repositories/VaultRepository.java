package com.example.lostaf.lvaultweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.lostaf.lvaultweb.entities.Vault;

import jakarta.transaction.Transactional;

import java.util.List;


public interface VaultRepository extends JpaRepository<Vault, Integer> {
    public List<Vault> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Vault v set v.name = ?1 where v.id = ?2")
    public Integer updateNameById(String name, Integer id);
}
