package br.com.meli.bootcamp.wave2.quality.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HouseSquaredMetersResponse {

  private String propertyName;
  private BigDecimal area;
}
