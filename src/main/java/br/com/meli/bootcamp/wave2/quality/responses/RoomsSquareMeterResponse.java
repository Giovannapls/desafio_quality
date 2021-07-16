package br.com.meli.bootcamp.wave2.quality.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomsSquareMeterResponse {

  private String propertyName;
  private List<RoomResponse> rooms;
}
