package br.com.meli.bootcamp.wave2.quality.service;

import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.response.RealEstatePropertiesResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class RoomService {

    public BigDecimal calculateSquaredMeters(PropertyPayload house) {
        return house.getRooms().stream()
                .map(this::calculateSquaredMeters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateSquaredMeters(PropertyPayload.RoomPayload room) {
        return room.getRoomLength().multiply(room.getRoomWidth());
    }

    public BigDecimal getPropertyValue(PropertyPayload house) {
        return calculateSquaredMeters(house).multiply(house.getValueDistricM2());
    }

    public RealEstatePropertiesResponse.RoomResponse getBiggestRoom(PropertyPayload house) {
        return house.getRooms().stream()
                .max(Comparator.comparing(this::calculateSquaredMeters))
                .map(this::createRoomResponse)
                .orElseThrow();
    }

    public RealEstatePropertiesResponse getResponse(PropertyPayload house) {
        var rooms = house.getRooms().stream()
                .map(this::createRoomResponse)
                .collect(Collectors.toList());

        return RealEstatePropertiesResponse.builder()
                .name(house.getPropName())
                .area(calculateSquaredMeters(house))
                .district(house.getPropDistrict())
                .squaredMeterValue(house.getValueDistricM2())
                .value(getPropertyValue(house))
                .biggestRoom(getBiggestRoom(house))
                .rooms(rooms)
                .build();
    }

    private RealEstatePropertiesResponse.RoomResponse createRoomResponse(PropertyPayload.RoomPayload room) {
        return new RealEstatePropertiesResponse.RoomResponse(room.getRoomName(),
                calculateSquaredMeters(room),
                room.getRoomWidth(),
                room.getRoomLength());
    }
}
