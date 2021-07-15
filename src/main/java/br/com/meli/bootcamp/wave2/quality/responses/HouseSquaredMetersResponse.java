package br.com.meli.bootcamp.wave2.quality.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class HouseSquaredMetersResponse {
    private String propertyName;
    private BigDecimal area;
}
