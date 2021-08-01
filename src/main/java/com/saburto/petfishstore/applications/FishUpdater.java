package com.saburto.petfishstore.applications;

import com.saburto.petfishstore.domain.model.UpdateFishRequest;
import com.saburto.petfishstore.domain.services.AquariumService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FishUpdater {

    private AquariumService aquariumService;

    public FishUpdater(AquariumService aquariumService) {
        this.aquariumService = aquariumService;
    }

    public void update(UpdateFishRequest request) {

        var oldFish = aquariumService.getFish(request.getSpecie());

        var updatedFish = request.merge(oldFish);

        var aquarium = aquariumService.getById(updatedFish.getAquariumId());

        var newAquarium = aquarium.withNewFish(updatedFish);

        aquariumService.updateFish(newAquarium);
    }

}
