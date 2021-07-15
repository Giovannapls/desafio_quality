package br.com.meli.bootcamp.wave2.quality.unit;

import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.response.RealEstatePropertiesResponse;
import br.com.meli.bootcamp.wave2.quality.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ServiceImplTest {

    private RoomService service;
    private DistrictRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = Mockito.mock(DistrictRepository.class);
        this.service = new RoomService(repository);
    }

    @Test
    void calculateSquaredMetersHouse_shouldCalculatePropertySquaredMetersCorrectly() {
        //arrange
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
                .valueDistricM2(new BigDecimal(100))
                .rooms(rooms)
                .build();
        //act
        var result = service.calculateSquaredMeters(house);

        //assert
        assertThat(result).isEqualTo(new BigDecimal(500));

    }

    @Test
    void getBiggestRoom_shouldReturnTheBiggestRoom() {
        //arrange
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
                .valueDistricM2(new BigDecimal(100))
                .rooms(rooms)
                .build();
        var roomResponse = new RealEstatePropertiesResponse.RoomResponse("Quarto de estudos", new BigDecimal(400),new BigDecimal(20), new BigDecimal(20));
        //act
        var result = service.getBiggestRoom(house);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(roomResponse);
    }

    @Test
    void calculateSquaredMetersRoom_shouldReturnSquareMetersCorrectly() {
        //arrange
        var room =
                PropertyPayload.RoomPayload.builder()
                        .roomName("Quarto azul e pequeno")
                        .roomLength(new BigDecimal(10))
                        .roomWidth(new BigDecimal(10))
                        .build();

        //act
        var result = service.calculateSquaredMeters(room);

        //assert
        assertThat(result).isEqualTo(new BigDecimal(100));

    }

    @Test
    void getResponse_shouldReturnRealEstatePropertiesCorrectly() {

    }
}
