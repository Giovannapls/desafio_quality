package br.com.meli.bootcamp.wave2.quality.controller;

import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.responses.BiggestRoomResponse;
import br.com.meli.bootcamp.wave2.quality.responses.HouseSquaredMetersResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RealEstateValueResponse;
import br.com.meli.bootcamp.wave2.quality.responses.RoomsSquareMeterResponse;
import br.com.meli.bootcamp.wave2.quality.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/realEstate")
public class RealEstateController {

    @Autowired
    private RoomService service;

    @PostMapping("/propertyArea")
    @ResponseStatus(HttpStatus.OK)
    public HouseSquaredMetersResponse calculateSquaredMeters(@RequestBody @Valid PropertyPayload house){
        return service.getArea(house);
    }

    @PostMapping("/propertyPrice")
    public RealEstateValueResponse calculateValue(@RequestBody @Valid PropertyPayload house){
        return service.getPropertyValue(house);
    }

    @PostMapping("/biggestRoom")
    public BiggestRoomResponse getBiggestRoom(@RequestBody @Valid PropertyPayload house){
        return service.getBiggestRoom(house);
    }

    @PostMapping("/roomArea")
    public RoomsSquareMeterResponse getRoomsArea(@RequestBody @Valid PropertyPayload house){
        return service.getRoomsArea(house);
    }



}
