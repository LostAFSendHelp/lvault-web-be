package com.example.lostaf.lvaultweb.entities;

import java.util.UUID;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vault")
public class Vault {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Integer createdAt;

    @Column(name = "last_modified")
    private Integer lastModified;
}
