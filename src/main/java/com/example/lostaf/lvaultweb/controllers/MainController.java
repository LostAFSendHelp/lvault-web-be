package com.example.lostaf.lvaultweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.lostaf.lvaultweb.entities.Vault;
import com.example.lostaf.lvaultweb.exceptions.BadRequestException;
import com.example.lostaf.lvaultweb.exceptions.DataNotFoundException;
import com.example.lostaf.lvaultweb.exceptions.DuplicatedException;
import com.example.lostaf.lvaultweb.models.Result;
import com.example.lostaf.lvaultweb.repositories.VaultRepository;
import com.example.lostaf.lvaultweb.utils.StringUtils;

import io.micrometer.common.lang.NonNull;

@Controller
public class MainController {
    
    @Autowired
    private VaultRepository vaultRepository;

    @PostMapping("vaults/add")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Result<Vault> addNewVault(
        @RequestParam @NonNull String name
    ) {
        String vaultName = name.trim();

        if (vaultName == null || vaultName.isEmpty() || !StringUtils.isAlphanumeric(vaultName)) {
            throw new BadRequestException();
        }

        // TODO: implement using @Column(unique=true)
        if (!vaultRepository.findByName(vaultName).isEmpty()) {
            throw new DuplicatedException("Vault with name \"" + vaultName + "\" already exists");
        }

        Vault vault = new Vault();
        vault.setName(name);
        vaultRepository.save(vault);
        
        return Result.<Vault>builder()
            .isSuccess(true)
            .data(vault)
            .statusCode(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("vault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Result<List<Vault>> getMethodName(
        @PathVariable("id") Integer param
    ) {
        List<Vault> vaults = vaultRepository.findAllById(List.of(param));

        if (vaults.isEmpty()) {
            throw new DataNotFoundException();
        }

        return Result.<List<Vault>>builder()
            .isSuccess(true)
            .statusCode(HttpStatus.OK)
            .data(vaults)
            .build();
    }
    

    @GetMapping("vaults")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Result<List<Vault>> getAllVaults() {
        List<Vault> data = vaultRepository.findAll();

        return Result.<List<Vault>>builder()
            .isSuccess(true)
            .data(data)
            .statusCode(HttpStatus.OK)
            .build();
    }

    @DeleteMapping("vault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Result<Boolean> deleteVault(
        @PathVariable @NonNull Integer id
    ) {
        vaultRepository.deleteById(id);
        return Result.<Boolean>builder()
            .isSuccess(true)
            .statusCode(HttpStatus.OK)
            .build();
    }

    @PatchMapping("vault/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Result<Boolean> updateNameById(
        @PathVariable @NonNull Integer id,
        @RequestParam @NonNull String name
    ) {
        String vaultName = name.trim();

        if (vaultName == null || vaultName.isEmpty() || !StringUtils.isAlphanumeric(vaultName)) {
            throw new BadRequestException();
        }

        Integer updated = vaultRepository.updateNameById(vaultName, id);
        
        return Result.<Boolean>builder()
            .isSuccess(true)
            .statusCode(HttpStatus.OK)
            .message("Updated " + updated + " vault(s)")
            .build();
    }
}
