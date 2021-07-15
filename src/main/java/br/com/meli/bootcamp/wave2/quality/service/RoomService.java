package br.com.meli.bootcamp.wave2.quality.service;

import br.com.meli.bootcamp.wave2.quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class RoomService {

    DistrictRepository repository;

    @Autowired
    public RoomService(DistrictRepository repository) {
        this.repository = repository;
    }


    private BigDecimal calculateSquaredMeters(PropertyPayload house) {
        return house.getRooms().stream()
                .map(this::calculateSquaredMeters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSquaredMeters(PropertyPayload.RoomPayload room) {
        return room.getRoomLength().multiply(room.getRoomWidth());
    }

    private BigDecimal getPropertyValue(PropertyPayload house, BigDecimal squareMeterValue) {
        return calculateSquaredMeters(house).multiply(squareMeterValue);
    }

    public BiggestRoomResponse getBiggestRoom(PropertyPayload house) {
        this.assertsThatDistrictExists(house);

        return house.getRooms().stream()
                .max(Comparator.comparing(this::calculateSquaredMeters))
                .map(this::createRoomResponse)
                .map(roomResponse -> new BiggestRoomResponse(house.getPropName(), roomResponse))
                .orElseThrow();
    }

    public RealEstateValueResponse getPropertyValue(PropertyPayload house) {
        var squaredMeterValue = repository.findByName(house.getPropDistrict())
                .orElseThrow(DistrictNotFoundException::new)
                .getSquareMeterPrice();

        return new RealEstateValueResponse(house.getPropName(), this.getPropertyValue(house, squaredMeterValue));
    }

    private RoomResponse createRoomResponse(PropertyPayload.RoomPayload room) {
        return new RoomResponse(room.getRoomName(),
                calculateSquaredMeters(room),
                room.getRoomWidth(),
                room.getRoomLength());
    }

    public HouseSquaredMetersResponse getArea(PropertyPayload house) {
        this.assertsThatDistrictExists(house);
        return new HouseSquaredMetersResponse(house.getPropName(), this.calculateSquaredMeters(house));
    }

    public RoomsSquareMeterResponse getRoomsArea(PropertyPayload house) {
        this.assertsThatDistrictExists(house);

        var roomsResponse = house.getRooms().stream()
                .map(this::createRoomResponse)
                .collect(Collectors.toList());

        return new RoomsSquareMeterResponse(house.getPropName(), roomsResponse);
    }

    private void assertsThatDistrictExists(String name){
        if(!repository.existsByName(name)) throw new DistrictNotFoundException();
    }

    private void assertsThatDistrictExists(PropertyPayload house){
        assertsThatDistrictExists(house.getPropDistrict());
    }
}
