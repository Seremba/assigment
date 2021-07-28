package com.saburto.petfishstore.api;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;
import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.Views;
import com.saburto.petfishstore.repositories.AquariumRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aquariums")
public class AquariumController {

    private AquariumRepository repository;

    public AquariumController(AquariumRepository repository) {
        this.repository = repository;
    }

    @JsonView(Views.Public.class)
    @GetMapping
    public List<Aquarium> all() {
        return repository.allAquariums();
    }

    @GetMapping("/{id}/fishes")
    public Set<Fish> fishes(@PathVariable("id") UUID id) {
        return repository.findSpeciesByAquariumId(id);
    }

}
