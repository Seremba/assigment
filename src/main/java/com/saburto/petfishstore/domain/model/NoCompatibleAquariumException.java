package com.saburto.petfishstore.domain.model;

import java.util.Collection;

public class NoCompatibleAquariumException extends RuntimeException {

    public NoCompatibleAquariumException(String newSpecie, Collection<String> noCompatibles) {
        super(String.format("%s can't go in the same aquarium as %s", newSpecie, noCompatibles));
    }

}
