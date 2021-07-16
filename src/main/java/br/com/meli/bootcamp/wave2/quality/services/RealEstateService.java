package br.com.meli.bootcamp.wave2.quality.services;

import br.com.meli.bootcamp.wave2.quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.repositories.DistrictRepository;
import br.com.meli.bootcamp.wave2.quality.responses.BiggestRoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.HouseSquaredMetersResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RealEstateValueResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomsSquareMeterResponse;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService {

  DistrictRepository repository;

  @Autowired
  public RealEstateService(DistrictRepository repository) {
    this.repository = repository;
  }

  /**
   * Get the biggest room of the given property
   *
   * @param payload - User input
   * @return Human friendly response
   */
  public BiggestRoomResponse getBiggestRoom(PropertyPayload payload) {
    this.assertsThatDistrictExists(payload);

    return payload.getRooms().stream()
                  .max(Comparator.comparing(this::calculateSquaredMeters))
                  .map(this::createRoomResponse)
                  .map(roomResponse -> new BiggestRoomResponse(payload.getPropName(), roomResponse))
                  .orElseThrow();
  }

  /**
   * Get the value of the given property, using the price per square meter of the district
   *
   * @param payload - User input
   * @return Human friendly response
   */
  public RealEstateValueResponse getPropertyValue(PropertyPayload payload) {
    var squaredMeterValue = repository.findByName(payload.getPropDistrict())
                                      .orElseThrow(DistrictNotFoundException::new)
                                      .getSquareMeterPrice();

    return new RealEstateValueResponse(payload.getPropName(),
        this.getPropertyValue(payload, squaredMeterValue));
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

  /**
   * Create a human friendly response of a single room, containing its measures, and the total area
   * Used internally in the service
   * @param payload - User input
   * @return Human friendly response
   */
  private RoomResponse createRoomResponse(PropertyPayload.RoomPayload payload) {
    return new RoomResponse(payload.getRoomName(),
        calculateSquaredMeters(payload),
        payload.getRoomWidth(),
        payload.getRoomLength());
  }


  /**
   * Calculate the total square meters of a property
   * @param property - user inputted property
   * @return Total area
   */
  private BigDecimal calculateSquaredMeters(PropertyPayload property) {
    return property.getRooms().stream()
                .map(this::calculateSquaredMeters)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * @param room - Room inputted by the user
   * @return Total area
   */
  private BigDecimal calculateSquaredMeters(PropertyPayload.RoomPayload room) {
    return room.getRoomLength().multiply(room.getRoomWidth());
  }

  /**
   *
   * @param property User inputted property
   * @param squareMeterValue - Value of the square meter of the district that the property is located
   * @return Value of the property
   */
  private BigDecimal getPropertyValue(PropertyPayload property, BigDecimal squareMeterValue) {
    return calculateSquaredMeters(property).multiply(squareMeterValue);
  }

  /**
   * Asserts that a given district exists, if not throws an exception
   * @throws DistrictNotFoundException - If the district isn't found
   * @param property - Property that will be used to get the district
   */
  private void assertsThatDistrictExists(PropertyPayload property) {
    assertsThatDistrictExists(property.getPropDistrict());
  }

  /**
   * Asserts that a given district exists, if not throws an exception
   * @throws DistrictNotFoundException - If the district isn't found
   * @param name - Name of the district
   */
  private void assertsThatDistrictExists(String name) {
    if (!repository.existsByName(name)) {
      throw new DistrictNotFoundException();
    }
  }
}
