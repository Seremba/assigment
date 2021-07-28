package com.saburto.petfishstore.applications;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.saburto.petfishstore.api.NewFishRequestInput;
import com.saburto.petfishstore.api.NewFishRequestInput.NewFishRequestInputBuilder;
import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.Fish;
import com.saburto.petfishstore.domain.model.Size;
import com.saburto.petfishstore.domain.services.AquariumService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FishCreatorTest {

    private FishCreator creator;
    private AquariumService aquariumService;

    @BeforeEach
    void setup() {
        aquariumService = mock(AquariumService.class);
        creator = new FishCreator(aquariumService);
    }

    @Test
    void create_new_fish_and_put_in_an_aquarium_that_doesnt_exist_throw_exception() {
        var noExistAquarium = UUID.randomUUID();

        when(aquariumService.getById(noExistAquarium))
            .thenThrow(new RuntimeException(noExistAquarium.toString() + " not found"));


        var newFish = newGoldFish(noExistAquarium);

        assertThatThrownBy(() -> creator.newFishInAquarium(newFish))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining(noExistAquarium.toString())
            .hasMessageContaining("not found");
    }

    @Test
    void create_new_fish_and_put_in_an_empty_aquarium() {
        var emptyAquarium = UUID.randomUUID();
        when(aquariumService.getById(emptyAquarium)).thenReturn(Aquarium.builder().build());

        var newFish = newGoldFish(emptyAquarium);

        creator.newFishInAquarium(newFish);

        verify(aquariumService).addNewFish(any());
    }

    @Test
    void create_new_goldfish_and_put_in_an_aquarium_with_guppies_should_throw_exception() {

        var aquariumWithGuppies = UUID.randomUUID();

        when(aquariumService.getById(aquariumWithGuppies))
            .thenReturn(Aquarium.builder().fish(newFish("guppy")).build());

        var newFish = newGoldFish(aquariumWithGuppies);

        assertThatThrownBy(() -> creator.newFishInAquarium(newFish))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("goldfish can't go in the same aquarium as [guppy]");

    }

    @Test
    void create_new_guppy_and_put_in_an_aquarium_with_goldfish_should_throw_exception() {

        var aquariumWithGoldfish = UUID.randomUUID();

        when(aquariumService.getById(aquariumWithGoldfish))
            .thenReturn(Aquarium.builder().fish(newGoldFish(aquariumWithGoldfish).toFish()).build());

        var newFish = newFishBuilder(aquariumWithGoldfish)
            .clearNoCompatibleSpecies()
            .specie("guppy")
            .build();

        assertThatThrownBy(() -> creator.newFishInAquarium(newFish)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("guppy can't go in the same aquarium as [goldfish]");

    }

    @Test
    void create_a_fish_with_3_fins_in_a_small_aquarium_should_throw_exception() {

        var smallAquarium = UUID.randomUUID();

        when(aquariumService.getById(smallAquarium))
            .thenReturn(Aquarium.builder().size(Size.ofLiters(12)).build());

        var fishWith3Fins = newFishBuilder(smallAquarium)
            .fins(3)
            .build();

        assertThatThrownBy(() -> creator.newFishInAquarium(fishWith3Fins))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("don't go in aquariums of 75 liters or less");

    }

    private NewFishRequestInput newGoldFish(UUID aquariumId) {
        return newFishBuilder(aquariumId)
            .build();
    }

    private NewFishRequestInputBuilder newFishBuilder(UUID aquariumId) {
        return NewFishRequestInput.builder()
            .specie("goldfish")
            .color(Colors.GOLDEN)
            .fins(1)
            .noCompatibleSpecie("guppy")
            .aquariumId(aquariumId.toString());
    }

    private Fish newFish(String specie) {
        return Fish.builder().specie(specie).build();
    }

}
