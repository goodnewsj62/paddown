package com.paddown.paddown.data;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column(name="nid",  nullable = false)
    private String nid;

    @NotNull
    @Column(name="content",  columnDefinition = "TEXT")
    private String content;

    @Column(name="date_created",  updatable = false,  nullable=false)
    private Date  date_created;

    @Column(name="date_created",  nullable=false)
    private Date last_updated;

    private Account creator;


    private Collection collection;

}
