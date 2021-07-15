package br.com.meli.bootcamp.wave2.quality.unit;

import br.com.meli.bootcamp.wave2.quality.entities.District;
import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.responses.RealEstateValueResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomsSquareMeterResponse;
import br.com.meli.bootcamp.wave2.quality.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ServiceImplTest {

    private RoomService service;
    private DistrictRepository repository;
    private PropertyPayload house;

    @BeforeEach
    void setUp() {
        this.repository = Mockito.mock(DistrictRepository.class);
        this.service = new RoomService(repository);
        this.house = createSetUpHouse();
    }

    private PropertyPayload createSetUpHouse(){
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
        var house = PropertyPayload.builder()
                .propName("Casa amarela")
                .propDistrict("Vila pauliceia")
                .rooms(rooms)
                .build();

        return house;
    }
    @Test
    void getArea_shouldCalculatePropertySquaredMetersCorrectly() {
        //arrange
        Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);

        //act
        var result = service.getArea(house);

        //assert
        assertThat(result.getArea()).isEqualTo(new BigDecimal(500));

    }

    @Test
    void getBiggestRoom_shouldReturnTheBiggestRoom() {
        //arrange
        Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);

        var roomResponse = new RoomResponse("Quarto de estudos", new BigDecimal(400),new BigDecimal(20), new BigDecimal(20));

        //act
        var result = service.getBiggestRoom(house);

        //assert
        assertThat(result.getBiggestRoom()).usingRecursiveComparison().isEqualTo(roomResponse);
    }

    @Test
    void calculateSquaredMetersRoom_shouldReturnSquareMetersCorrectly() {
        //arrange
        Mockito.when(repository.existsByName("Vila pauliceia")).thenReturn(true);

        var expectedRoomsResponse = Arrays.asList(
                new RoomResponse("Quarto azul e pequeno", bd(100), bd(10), bd(10)),
                new RoomResponse("Quarto de estudos", bd(400), bd(20), bd(20))
                );

        var expectedRoomsSquaredMetersResponse = new RoomsSquareMeterResponse(house.getPropName(), expectedRoomsResponse);

        //act
        var result = service.getRoomsArea(house);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedRoomsSquaredMetersResponse);

    }
    private BigDecimal bd(int i){
        return new BigDecimal(i);
    }

    @Test
    void getPropertyValue_shouldReturnPropertyValueCorrectly() {
        //arrange
        Mockito.when(repository.findByName("Vila pauliceia")).thenReturn(java.util.Optional.of(new District("Vila pauliceia", bd(10))));

        var expectedRealEstateValueResponse = new RealEstateValueResponse(house.getPropName(), bd(5000));

        //act
        var result = service.getPropertyValue(house);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedRealEstateValueResponse);
    }
}
