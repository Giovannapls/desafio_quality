package br.com.meli.bootcamp.wave2.quality.controller;

import br.com.meli.bootcamp.wave2.quality.forms.PropertyPayload;
import br.com.meli.bootcamp.wave2.quality.response.RealEstatePropertiesResponse;
import br.com.meli.bootcamp.wave2.quality.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/realEstate")
public class RealEstateController {

    @Autowired
    private RoomService service;

    @PostMapping
    public RealEstatePropertiesResponse calculateSquaredMeters(@RequestBody @Valid PropertyPayload house){
        return service.getResponse(house);
    }


}
