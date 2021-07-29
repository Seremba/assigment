package com.saburto.petfishstore.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoFishFoundException extends RuntimeException {

    public NoFishFoundException(String specie) {
        super(specie + " not found ");
    }

}
