package br.com.meli.bootcamp.wave2.quality.exceptions;

import org.springframework.http.HttpStatus;

public class DistrictNotFoundException extends ApiException{
    public DistrictNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "invalid_district_name", "District does not exist");
    }
}
