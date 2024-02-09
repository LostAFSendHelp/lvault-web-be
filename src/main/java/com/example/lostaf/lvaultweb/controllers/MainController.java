package com.example.lostaf.lvaultweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.lostaf.lvaultweb.entities.Vault;
import com.example.lostaf.lvaultweb.repositories.VaultRepository;

@Controller
@RequestMapping(path = "/vaults")
public class MainController {
    
    @Autowired
    private VaultRepository vaultRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewVault(
        @RequestParam String name
    ) {
        Vault vault = new Vault();
        vault.setName(name);
        vaultRepository.save(vault);
        return vault.getId().toString();
    }

    @GetMapping
    public @ResponseBody Iterable<Vault> getAllVaults() {
        return vaultRepository.findAll();
    }
}
