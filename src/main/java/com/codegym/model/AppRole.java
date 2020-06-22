package com.codegym.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table
public class AppRole implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public AppRole() {
    }

    @Override
    public String getAuthority() {
        return this.name;
    }


    public AppRole() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
