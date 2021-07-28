package com.saburto.petfishstore.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.saburto.petfishstore.applications.FishCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fish")
@Validated
public class FishController {

    private FishCreator fishCreator;

    @Autowired
    public FishController(FishCreator fishCreator) {
        this.fishCreator = fishCreator;
    }

    @PostMapping
    public void newFish(@RequestBody @NotNull @Valid NewFishRequestInput input) {
        fishCreator.newFishInAquarium(input);
    }
}
