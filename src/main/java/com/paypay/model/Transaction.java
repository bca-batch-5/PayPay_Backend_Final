package com.paypay.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransaction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "note")
    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from", referencedColumnName = "id_user")
    private User from;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "to", referencedColumnName = "id_user")
    private User to;

    @Column(name = "transaction_type")
    private String type;

    @Column(name = "date")
    private LocalDateTime date;
}
