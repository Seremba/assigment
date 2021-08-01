package com.saburto.petfishstore.domain.model;

import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Fish {
    private static final int FINS_OF_BIG_FISH = 3;
    private String specie;
    private Integer fins;
    private Colors color;
    private Integer stock;
    private Set<String> noCompatibleSpecies;
    private String aquariumId;

    public boolean isBig() {
        return fins >= FINS_OF_BIG_FISH;
    }

}
