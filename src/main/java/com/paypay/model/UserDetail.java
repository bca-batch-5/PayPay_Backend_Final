package com.paypay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_detail")
public class UserDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetailUser;
    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;
    @Column(name = "nama")
    private String nama;
    @Column(name = "no_telpon")
    private String noTelp;
    @Column(name = "saldo")
    private Long saldo;
    @Column(name = "image")
    private String image;

}
