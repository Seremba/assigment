package com.saburto.petfishstore.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.Views;
import com.saburto.petfishstore.repositories.AquariumRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @JsonView(Views.Public.class)
    @GetMapping("/{id}")
    public Aquarium findById(@PathVariable("id") String id) {
        return Optional.ofNullable(repository.findById(id))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                           id.toString() + " not found"));
    }


    @GetMapping("/{id}/fishes")
    public Set<Fish> fishes(@PathVariable("id") String id) {
        return repository.findSpeciesByAquariumId(id);
    }

}
