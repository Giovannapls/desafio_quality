package br.com.meli.bootcamp.wave2.quality.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    @Positive
    private BigDecimal squareMeterPrice;

    public District(String name, BigDecimal squareMeterPrice) {
        this.name = name;
        this.squareMeterPrice = squareMeterPrice;
    }
}
