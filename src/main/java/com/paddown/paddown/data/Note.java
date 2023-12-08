package com.paddown.paddown.data;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.paddown.paddown.utils.Base64EncodedUUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column(name="nid",  nullable = false)
    private String nid;

    @NotNull
    @Column(name="content",  columnDefinition = "TEXT")
    private String content;
    
    @NotBlank
    @NotNull
    @Column(name="title",  columnDefinition = "TEXT")
    private String title;

    @Column(name="date_created",  updatable = false,  nullable=false)
    @CreationTimestamp
    private OffsetDateTime  dateCreated;

    @Column(name="last_updated",  nullable=false)
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;

    @ManyToOne(optional = false)
    @JoinColumn(name="creator_id",  referencedColumnName ="id" )
    private Account creator;

    @ManyToOne(optional = true)
    @JoinColumn(name="collection_id",  referencedColumnName = "id")
    private Collection collection;

    private Base64EncodedUUID b64string;
    

    public Note(Base64EncodedUUID b64str ,String title,  String content){
        this.title =  title;
        this.content =  content;
        if(this.nid.length() <  1){
            this.b64string =  b64str;
            this.nid =  this.b64string.getBase64EncodedUUID();
        }
    }


}
