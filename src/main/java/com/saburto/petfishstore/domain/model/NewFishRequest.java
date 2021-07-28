package com.saburto.petfishstore.domain.model;

import java.util.Set;
import java.util.UUID;

public interface NewFishRequest {

    String getSpecie();
    Colors getColor();
    int getFins();
    UUID getAquariumID();
    Set<String> getNoCompatibleSpecies();

    default Fish toFish() {
        return Fish.builder()
            .specie(getSpecie())
            .color(getColor())
            .noCompatibleSpecies(getNoCompatibleSpecies())
            .fins(getFins())
            .build();
    }

}
