package br.com.meli.bootcamp.wave2.quality.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class RealEstatePropertiesResponse {
    private String name;
    private BigDecimal area;
    private String district;
    private BigDecimal squaredMeterValue;
    private BigDecimal value;
    private RoomResponse biggestRoom;
    private List<RoomResponse> rooms;

    @AllArgsConstructor
    @Getter
    public static class RoomResponse{
        private String name;
        private BigDecimal area;
        private BigDecimal width;
        private BigDecimal length;
    }
}
