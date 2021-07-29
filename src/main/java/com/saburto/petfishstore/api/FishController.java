package com.saburto.petfishstore.api;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.saburto.petfishstore.applications.FishCreator;
import com.saburto.petfishstore.applications.FishUpdater;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.repositories.AquariumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/fish")
@Validated
public class FishController {

    private FishCreator fishCreator;
    private FishUpdater fishUpdater;
    private AquariumRepository repo;

    @Autowired
    public FishController(FishCreator fishCreator, FishUpdater fishUpdater, AquariumRepository repo) {
        this.fishCreator = fishCreator;
        this.fishUpdater = fishUpdater;
        this.repo = repo;
    }

    @PostMapping
    @Validated(Validate.OnCreate.class)
    public void newFish(@RequestBody @NotNull @Valid FishRequestInput input) {
        fishCreator.newFishInAquarium(input);
    }

    @PutMapping
    @Validated(Validate.OnUpdate.class)
    public void update(@RequestBody @NotNull @Valid FishRequestInput input) {
        fishUpdater.update(input);
    }

    @GetMapping("/{specie}")
    public Fish getFish(@PathVariable("specie") String specie) {
        return Optional.ofNullable(repo.findFishById(specie))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                           specie + " not found"));
    }
}
