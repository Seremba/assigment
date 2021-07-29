package com.saburto.petfishstore.domain.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoAquariumFoundException extends RuntimeException {

    public NoAquariumFoundException(UUID aquariumID) {
        super(String.format("No aquarium with the id [%s] found", aquariumID.toString()));
    }

}
