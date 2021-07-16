package br.com.meli.bootcamp.wave2.quality.responses;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomResponse {

  private String name;
  private BigDecimal area;
  private BigDecimal width;
  private BigDecimal length;
}
