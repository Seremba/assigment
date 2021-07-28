package com.saburto.petfishstore.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.Size;
import com.saburto.petfishstore.repositories.AquariumRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AquariumServiceTest {

    private AquariumRepository mock;
    private AquariumService service;

    @BeforeEach
    void setup() {
        mock = mock(AquariumRepository.class);
        service = new AquariumService(mock);
    }

    @Test
    void return_empty_aquarium() {
        var response = Aquarium.builder().build();
        when(mock.findById(any())).thenReturn(response);

        var aq = service.getById(UUID.randomUUID());
        assertThat(aq).isEqualTo(response);
    }

    @Test
    void throw_exception_if_aquarium_is_not_found() {
        when(mock.findById(any())).thenReturn(null);

        assertThatThrownBy(() -> service.getById(UUID.randomUUID()))
            .isInstanceOf(NoAquariumFoundException.class);
    }

    @Test
    void return_no_empty_aquarium() {
        var response = Aquarium.builder().build();
        when(mock.findById(any())).thenReturn(response);
        var fishes = Set.of(newFish("goldfish"), newFish("octopus"), newFish("swordfish"));
        when(mock.findSpeciesByAquariumId(any()))
            .thenReturn(fishes);

        var aq = service.getById(UUID.randomUUID());

        assertThat(aq.getFishes()).isEqualTo(fishes);
    }

    @Test
    void verify_insert_fish_is_called() {
        var id = UUID.randomUUID();
        var aq = Aquarium.builder().id(id)
            .size(Size.ofLiters(78))
            .build()
            .withNewFish(newFish("swordfish"));
        service.addNewFish(aq);

        verify(mock).insertFish(anyString(), anyInt(), any(), anyInt(), any());
    }

    private Fish newFish(String specie) {
        return Fish.builder().specie(specie)
            .noCompatibleSpecies(Set.of())
            .build();
    }
}
