package com.saburto.petfishstore.domain.model;

import static java.util.Objects.requireNonNullElse;

import java.util.Set;

public interface UpdateFishRequest extends NewFishRequest {

    default Fish merge(Fish original) {
        return Fish.builder()
            .specie(getSpecie())
            .color(requireNonNullElse(getColor(), original.getColor()))
            .stock(requireNonNullElse(getStock(), original.getStock()))
            .noCompatibleSpecies(requireNoEmpty(getNoCompatibleSpecies(),
                                                original.getNoCompatibleSpecies()))
            .fins(requireNonNullElse(getFins(), original.getFins()))
            .build();
    }

    static Set<String> requireNoEmpty(Set<String> noCompatibleSpecies,
                                      Set<String> noCompatibleSpeciesOriginal) {
        return noCompatibleSpecies == null || noCompatibleSpecies.isEmpty() ?
            noCompatibleSpeciesOriginal : noCompatibleSpecies;
        }
}
