package com.tinnova.cars.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String veiculo;
    private Marca marca;
    private Integer ano;
    private String descrição;

    @Column(columnDefinition = "boolean default false")
    private boolean vendido;

    //Prevent changing the create date
    @CreationTimestamp
    @Column(name = "Created", updatable = false)
    private Instant created;

    @UpdateTimestamp
    private Instant updated;
}
