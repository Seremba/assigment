package com.saburto.petfishstore.applications;

import com.saburto.petfishstore.domain.model.FishAlreadyExistsException;
import com.saburto.petfishstore.domain.model.NewFishRequest;
import com.saburto.petfishstore.domain.services.AquariumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FishCreator {

    private AquariumService aquariumService;

    @Autowired
    public FishCreator(AquariumService aquariumService) {
        this.aquariumService = aquariumService;
    }

    public void newFishInAquarium(NewFishRequest newFishRequest) {

        var aquarium = aquariumService.getById(newFishRequest.getAquariumID());

        var newAquarium = aquarium.withNewFish(newFishRequest.toFish());

        try {
            aquariumService.addNewFish(newAquarium);
        } catch (DuplicateKeyException ex) {
            throw new FishAlreadyExistsException(newFishRequest.getSpecie());
        }
    }

}
