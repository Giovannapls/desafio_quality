package br.com.meli.bootcamp.wave2.quality.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BiggestRoomResponse {
    private String propertyName;
    private RoomResponse biggestRoom;
}
