package com.saburto.petfishstore.domain.model;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoCompatibleAquariumException extends RuntimeException {

    public NoCompatibleAquariumException(String newSpecie, Collection<String> noCompatibles) {
        super(String.format("%s can't go in the same aquarium as %s", newSpecie, noCompatibles));
    }

}
