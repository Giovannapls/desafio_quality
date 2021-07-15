package br.com.meli.bootcamp.wave2.quality.service;
import br.com.meli.bootcamp.wave2.quality.exceptions.ApiException;
import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.response.RealEstatePropertiesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (repository.findByName(house.getPropDistrict()).isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid_district_name", "District does not exist");
        }

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
