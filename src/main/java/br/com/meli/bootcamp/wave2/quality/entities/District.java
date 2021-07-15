package br.com.meli.bootcamp.wave2.quality.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
}
