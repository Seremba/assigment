package com.saburto.petfishstore.domain.services;

import static java.util.stream.Collectors.toSet;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.Aquarium.AquariumBuilder;
import com.saburto.petfishstore.repositories.AquariumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AquariumService {

    private AquariumRepository repository;

    @Autowired
    public AquariumService(AquariumRepository repository) {
        this.repository = repository;
    }

    public Aquarium getById(UUID aquariumID) {
        return Optional.ofNullable(repository.findById(aquariumID))
            .map(Aquarium::toBuilder)
            .map(addFishes(aquariumID))
            .map(AquariumBuilder::build)
            .orElseThrow(() -> new NoAquariumFoundException(aquariumID));
    }

    private Function<AquariumBuilder, AquariumBuilder> addFishes(UUID aquariumID) {
        return builder -> builder.fishes(repository.findSpeciesByAquariumId(aquariumID));
    }

    public void addNewFish(Aquarium aquarium) {
        var fish = aquarium.getNewFish();
        repository.insertFish(fish.getSpecie(),
                              fish.getFins(),
                              fish.getColor(),
                              fish.getStock(),
                              aquarium.getId());
    }

}
