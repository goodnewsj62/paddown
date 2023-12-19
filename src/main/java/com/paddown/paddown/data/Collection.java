package com.paddown.paddown.data;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.paddown.paddown.utils.Base64EncodedUUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String cid;


    @Column(name="date_created",  updatable = false,  nullable=false)
    @CreationTimestamp
    private OffsetDateTime  dateCreated;

    @Column(name="last_updated",  nullable=false)
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;


    @OneToMany(mappedBy = "collection",  cascade = CascadeType.ALL)
    private List<Note> notes;

    @ManyToOne(optional = false)
    @JoinColumn(name="creator_id",  referencedColumnName ="id" )
    private Account creator;


    public Collection(){
        genCid();
    }

    public Collection(String name) {
        this();
        this.name = name;
    }

    public void genCid(){
        if(this.cid == null  || this.cid.length() <  1){
            this.cid =  Base64EncodedUUID.getBase64EncodedUUID();
        }
    }
    
}
