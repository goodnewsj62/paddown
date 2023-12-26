package com.paddown.paddown.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paddown.paddown.utils.Base64EncodedUUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="id")
    private  Long id;

    @NotBlank
    @NotNull
    private String uid;

    @NotBlank(message = "email cannot be left blank")
    @NotNull(message = "email cannot be null")
    @Column(name = "email",  nullable = false, unique = true)
    private String email;

    @Column(name="password",  nullable = true)
    private String password =  null;
    
    @Column(name="is_active")
    private boolean isActive =  true;

    @OneToMany(mappedBy ="creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private  List<Collection> collections;

    @OneToMany(mappedBy ="creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private  List<Note> notes;

    @Column(name="is_staff")
    private boolean isStaff =  false;

    @Column(name="is_superuser")
    private boolean isSuperUser =  false;

    @Column(name="image")
    private String image;



    //TODO:  add day created

    public Account(){
        this.genUid();
    }
    
    public Account(String email,  String password, boolean is_active){
        this();
        this.email =  email;
        this.password =  password;
        this.isActive =  is_active;
    }

    public void genUid(){
        if(this.uid == null  || this.uid.length() <  1){
            this.uid =  Base64EncodedUUID.getBase64EncodedUUID();
        }
    }
}
