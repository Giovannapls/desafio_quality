package br.com.meli.bootcamp.wave2.quality.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
