package br.com.meli.bootcamp.wave2.quality.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoomsSquareMeterResponse {
    private String propertyName;
    private List<RoomResponse> rooms;
}
