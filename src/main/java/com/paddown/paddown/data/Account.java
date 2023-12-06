package com.paddown.paddown.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="id")
    private  Long id;

    @NotBlank(message = "email cannot be left blank")
    @NotNull(message = "email cannot be null")
    @Column(name = "email",  nullable = false, unique = true)
    private String email;

    @Column(name="password",  nullable = true)
    private String password;
    
    @Column(name="is_active")
    private boolean is_active =  true;

}
