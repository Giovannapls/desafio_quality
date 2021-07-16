package br.com.meli.bootcamp.wave2.quality.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RealEstateValueResponse {

  private String name;
  private BigDecimal value;
}
