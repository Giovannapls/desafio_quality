package br.com.meli.bootcamp.wave2.quality.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class RoomResponse {
    private String name;
    private BigDecimal area;
    private BigDecimal width;
    private BigDecimal length;
}
