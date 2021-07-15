package br.com.meli.bootcamp.wave2.quality.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RealEstateValueResponse {
    private String name;
    private BigDecimal value;
}
