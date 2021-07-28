package com.saburto.petfishstore.domain.model;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.util.Pair;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Aquarium {

    private static final int SMALL_LITERS = 75;

    @JsonView(Views.Public.class)
    UUID id;


    @JsonView(Views.Public.class)
    GlassType glassType;


    @JsonView(Views.Public.class)
    Size size;


    @JsonView(Views.Public.class)
    Shape shape;

    @Singular("fish")
    Set<Fish> fishes;

    private Fish newFish;

    public Aquarium withNewFish(Fish newFish) {

        checkIfAreNotIncompatibleSpecies(newFish);
        checkIfCurrentSpeciesTheNewFishIsCompatible(newFish);
        checkIfFishFits(newFish);

        var fishAdded = newFish.toBuilder()
              .aquariumId(id).build();

        return toBuilder()
            .fish(fishAdded)
            .newFish(fishAdded)
            .build();
    }

    private void checkIfFishFits(Fish newFish) {
        if (newFish.isBig() && this.isSmall()) {
            throw new FishWithThreeFinsCantGoInSmallAquariumException();
        }
    }

    private boolean isSmall() {
        return getSize().getLiters() <= SMALL_LITERS;
    }

    private void checkIfAreNotIncompatibleSpecies(Fish newFish) {
        var noCompatibles = fishes.stream()
            .map(Fish::getSpecie)
            .filter(newFish.getNoCompatibleSpecies()::contains)
            .collect(toSet());

        if (!noCompatibles.isEmpty()) {
            throw new NoCompatibleAquariumException(newFish.getSpecie(), noCompatibles);
        }


    }

    private void checkIfCurrentSpeciesTheNewFishIsCompatible(Fish newFish) {
        var noCompatibles = fishes.stream()
            .flatMap(f -> f.getNoCompatibleSpecies().stream().map( s -> Pair.of(f.getSpecie(), s)))
            .filter(p -> p.getSecond().equals(newFish.getSpecie()))
            .map(p -> p.getFirst())
            .collect(toSet());

        if (!noCompatibles.isEmpty()) {
            throw new NoCompatibleAquariumException(newFish.getSpecie(), noCompatibles);
        }
    }

    public Set<String> geSpecies() {
        return fishes.stream()
            .map(Fish::getSpecie)
            .collect(toSet());
    }

}
