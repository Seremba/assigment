package com.saburto.petfishstore.domain.model;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Fish {
    private static final int FINS_OF_BIG_FISH = 3;
    private String specie;
    private int fins;
    private Colors color;
    private int stock;
    private Set<String> noCompatibleSpecies;
    private UUID aquariumId;

    public boolean isBig() {
        return fins >= FINS_OF_BIG_FISH;
    }

}
