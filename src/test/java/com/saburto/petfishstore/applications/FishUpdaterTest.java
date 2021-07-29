package com.saburto.petfishstore.applications;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.saburto.petfishstore.api.FishRequestInput;
import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.GlassType;
import com.saburto.petfishstore.domain.model.Size;
import com.saburto.petfishstore.domain.services.AquariumService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class FishUpdaterTest {

    private AquariumService service;
    private FishUpdater updater;

    @BeforeEach
    void setup() {
        service = mock(AquariumService.class);
        updater = new FishUpdater(service);
    }


    @Test
    void update_stock_of_a_fish() {

        when(service.getFish(anyString())).thenReturn(Fish.builder()
                                                      .color(Colors.RED)
                                                      .specie("some")
                                                      .fins(1)
                                                      .stock(1)
                                                      .aquariumId(UUID.randomUUID())
                                                      .noCompatibleSpecies(Set.of())
                                                      .build());
        when(service.getById(any())).thenReturn(Aquarium.builder()
                                                .fishes(List.of())
                                                .glassType(GlassType.STRONG)
                                                .size(Size.ofLiters(500))
                                                .build());

        var request = FishRequestInput.builder()
            .specie("some")
            .stock(10)
            .noCompatibleSpecies(List.of())
            .build();

        updater.update(request);

        var aquariumCaptor = ArgumentCaptor.forClass(Aquarium.class);
        verify(service).updateFish(aquariumCaptor.capture());

        var aquarium = aquariumCaptor.getValue();
        assertThat(aquarium.getNewFish().getStock()).isEqualTo(10);
    }

}
