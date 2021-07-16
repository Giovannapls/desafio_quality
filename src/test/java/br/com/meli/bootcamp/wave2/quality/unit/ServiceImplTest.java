package br.com.meli.bootcamp.wave2.quality.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.meli.bootcamp.wave2.quality.entities.District;
import br.com.meli.bootcamp.wave2.quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.responses.RealEstateValueResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomsSquareMeterResponse;
import br.com.meli.bootcamp.wave2.quality.services.RealEstateService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ServiceImplTest {

  private RealEstateService service;
  private DistrictRepository repository;
  private PropertyPayload house;

  @BeforeEach
  void setUp() {
    this.repository = Mockito.mock(DistrictRepository.class);
    this.service = new RealEstateService(repository);
    this.house = createSetUpHouse();
  }

  private PropertyPayload createSetUpHouse() {
    var rooms = Arrays.asList(
        PropertyPayload.RoomPayload.builder()
                                   .roomName("Quarto azul e pequeno")
                                   .roomLength(new BigDecimal(10))
                                   .roomWidth(new BigDecimal(10))
                                   .build(),
        PropertyPayload.RoomPayload.builder()
                                   .roomName("Quarto de estudos")
                                   .roomLength(new BigDecimal(20))
                                   .roomWidth(new BigDecimal(20))
                                   .build()
    );

    return PropertyPayload.builder()
                          .propName("Casa amarela")
                          .propDistrict("Vila pauliceia")
                          .rooms(rooms)
                          .build();
  }

  private BigDecimal bd(int i) {
    return new BigDecimal(i);
  }


  @Test
  void getArea_shouldCalculatePropertySquaredMetersCorrectly() {
    // Given
    Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);

    // When
    var result = service.getArea(house);

    // Then
    assertThat(result.getArea()).isEqualTo(new BigDecimal(500));

  }

  @Test
  void getBiggestRoom_shouldReturnTheBiggestRoom() {
    // Given
    Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);
    var roomResponse = new RoomResponse("Quarto de estudos", bd(400), bd(20), bd(20));

    // When
    var result = service.getBiggestRoom(house);

    // Then
    assertThat(result.getBiggestRoom()).usingRecursiveComparison().isEqualTo(roomResponse);
  }

  @Test
  void getRoomsArea_shouldReturnSquareMetersOfAllRoomsCorrectly() {
    // Given
    Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);
    var expectedRoomsResponse = Arrays.asList(
        new RoomResponse("Quarto azul e pequeno", bd(100), bd(10), bd(10)),
        new RoomResponse("Quarto de estudos", bd(400), bd(20), bd(20))
    );
    var expectedRoomsSquaredMetersResponse = new RoomsSquareMeterResponse(
        house.getPropName(),
        expectedRoomsResponse
    );

    // When
    var result = service.getRoomsArea(house);

    // Then
    assertThat(result).usingRecursiveComparison().isEqualTo(expectedRoomsSquaredMetersResponse);

  }


  @Test
  void getPropertyValue_shouldReturnPropertyValueCorrectly() {
    // Given
    Mockito.when(repository.findByName("Vila pauliceia"))
           .thenReturn(Optional.of(new District("Vila pauliceia", bd(10))));

    var expectedRealEstateValueResponse = new RealEstateValueResponse(
        house.getPropName(),
        bd(5000)
    );

    // When
    var result = service.getPropertyValue(house);

    // Then
    assertThat(result).usingRecursiveComparison().isEqualTo(expectedRealEstateValueResponse);
  }

  @Test
  void assertsThatDistrictExists_shouldThrowExceptionWhenDistrictDoesNotExists() {

    // Given
    Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(false);

    // When
    ThrowingCallable execution = () -> service.getRoomsArea(house);

    // Then
    assertThatThrownBy(execution)
        .isInstanceOf(DistrictNotFoundException.class);

  }

  @Test
  void getPropertyValue_shouldThrowExceptionWhenDistrictDoesNotExists() {

    // Given
    Mockito.when(repository.findByName("Vila pauliceia")).thenReturn(Optional.empty());

    // When
    ThrowingCallable execution = () -> service.getPropertyValue(house);

    // Then
    assertThatThrownBy(execution)
        .isInstanceOf(DistrictNotFoundException.class);
  }
}
