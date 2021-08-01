package com.saburto.petfishstore.applications;

import com.saburto.petfishstore.domain.model.UpdateFishRequest;
import com.saburto.petfishstore.domain.services.AquariumService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FishUpdater {

    private AquariumService aquariumService;

    public FishUpdater(AquariumService aquariumService) {
        this.aquariumService = aquariumService;
    }

    public void update(UpdateFishRequest request) {

        log.info("rq fish {}", request);
        var oldFish = aquariumService.getFish(request.getSpecie());

        log.info("old fish {}", oldFish);
        var updatedFish = request.merge(oldFish);
        log.info("updated fish {}", updatedFish);

        var aquarium = aquariumService.getById(updatedFish.getAquariumId());

        var newAquarium = aquarium.withNewFish(updatedFish);

        aquariumService.updateFish(newAquarium);
    }

}
