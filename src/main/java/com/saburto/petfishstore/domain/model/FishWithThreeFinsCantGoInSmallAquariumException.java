package com.saburto.petfishstore.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FishWithThreeFinsCantGoInSmallAquariumException extends RuntimeException {

    public FishWithThreeFinsCantGoInSmallAquariumException() {
        super("Fish with three fins or more don't go in aquariums of 75 liters or less");
    }

}
