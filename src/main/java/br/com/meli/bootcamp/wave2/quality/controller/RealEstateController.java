package br.com.meli.bootcamp.wave2.quality.controller;

import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.responses.BiggestRoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.HouseSquaredMetersResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RealEstateValueResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomsSquareMeterResponse;
import br.com.meli.bootcamp.wave2.quality.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(
        name = "Real Estate Controller",
        description = "Routes related to real estate calculations")
@Validated
@RestController
@RequestMapping("/realEstate")
public class RealEstateController {

    @Autowired
    private RoomService service;

    @Operation(
            summary = "Calculate property Area",
            description = "Return the Property area in Squared meters")
    @PostMapping("/propertyArea")
    @ResponseStatus(HttpStatus.OK)
    public HouseSquaredMetersResponse calculateSquaredMeters(@RequestBody @Valid PropertyPayload house){
        return service.getArea(house);
    }

    @Operation(
            summary = "Calculate property Price",
            description = "Return the Property total price based on its Squared meters")
    @PostMapping("/propertyPrice")
    public RealEstateValueResponse calculateValue(@RequestBody @Valid PropertyPayload house){
        return service.getPropertyValue(house);
    }

    @Operation(
            summary = "Calculate the biggest property room",
            description = "Return the Biggest room on the Property")
    @PostMapping("/biggestRoom")
    public BiggestRoomResponse getBiggestRoom(@RequestBody @Valid PropertyPayload house){
        return service.getBiggestRoom(house);
    }

    @Operation(
            summary = "Calculate the area of all rooms on the Property",
            description = "Return the area of all rooms on the Property in Squared meters")
    @PostMapping("/roomArea")
    public RoomsSquareMeterResponse getRoomsArea(@RequestBody @Valid PropertyPayload house){
        return service.getRoomsArea(house);
    }



}
