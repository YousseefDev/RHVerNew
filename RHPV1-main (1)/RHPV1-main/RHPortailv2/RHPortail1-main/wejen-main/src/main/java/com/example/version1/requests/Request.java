package com.example.version1.requests;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long  userId;
    private String title;
    private String description;
    @Column(columnDefinition = "boolean default false")
    private boolean isValid;
    private String typeRequest;
    private String service;
    private String statue;

}

