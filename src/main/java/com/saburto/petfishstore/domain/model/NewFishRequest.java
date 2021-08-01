package com.saburto.petfishstore.domain.model;

import java.util.Set;

public interface NewFishRequest {

    String getSpecie();
    Colors getColor();
    Integer getFins();
    Integer getStock();
    String getAquariumID();
    Set<String> getNoCompatibleSpecies();

    default Fish toFish() {
        return Fish.builder()
            .specie(getSpecie())
            .color(getColor())
            .stock(getStock())
            .noCompatibleSpecies(getNoCompatibleSpecies())
            .fins(getFins())
            .build();
    }

}
