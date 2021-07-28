package com.saburto.petfishstore.domain.model;

public class FishWithThreeFinsCantGoInSmallAquariumException extends RuntimeException {

    public FishWithThreeFinsCantGoInSmallAquariumException() {
        super("Fish with three fins or more don't go in aquariums of 75 liters or less");
    }

}
