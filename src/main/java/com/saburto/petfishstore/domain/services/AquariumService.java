package com.saburto.petfishstore.domain.services;

import java.util.Optional;
import java.util.function.Function;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Aquarium.AquariumBuilder;
import com.saburto.petfishstore.domain.model.Fish;
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

    public Aquarium getById(String aquariumID) {
        return Optional.ofNullable(repository.findById(aquariumID))
            .map(Aquarium::toBuilder)
            .map(addFishes(aquariumID))
            .map(AquariumBuilder::build)
            .orElseThrow(() -> new NoAquariumFoundException(aquariumID));
    }

    private Function<AquariumBuilder, AquariumBuilder> addFishes(String aquariumID) {
        return builder -> builder.fishes(repository.findSpeciesByAquariumId(aquariumID));
    }

    public void addNewFish(Aquarium aquarium) {
        var fish = aquarium.getNewFish();
        repository.insertFish(fish.getSpecie(),
                              fish.getFins(),
                              fish.getColor(),
                              fish.getStock(),
                              aquarium.getId());

        updateNoCompatibleFish(fish);
    }

    private void updateNoCompatibleFish(Fish fish) {
        repository.deleteNoCompatileFish(fish.getSpecie());
        fish.getNoCompatibleSpecies()
            .forEach(s -> repository.addNoCompatibleFish(fish.getSpecie(), s));
    }

    public void updateFish(Aquarium aquarium) {
        var fish = aquarium.getNewFish();
        repository.updateFish(fish.getSpecie(),
                              fish.getFins(),
                              fish.getColor(),
                              fish.getStock(),
                              aquarium.getId());

        updateNoCompatibleFish(fish);
    }

    public Fish getFish(String specie) {
        return Optional.ofNullable(repository.findFishById(specie))
            .orElseThrow(() -> new NoFishFoundException(specie));
    }

}
