package com.saburto.petfishstore.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FishAlreadyExistsException extends RuntimeException {

    public FishAlreadyExistsException(String specie) {
        super(specie + " already exists");
    }

}
