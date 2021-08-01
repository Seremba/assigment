package com.saburto.petfishstore.domain.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoAquariumFoundException extends RuntimeException {

    public NoAquariumFoundException(String aquariumID) {
        super(String.format("No aquarium with the id [%s] found", aquariumID));
    }

}
