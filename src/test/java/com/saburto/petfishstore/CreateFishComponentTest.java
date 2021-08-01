package com.saburto.petfishstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CreateFishComponentTest extends ComponentTestBase {


    @Test
    void given_an_aquarium_create_a_new_fish_in_it() {

        var aquariumId = givenAnAquariumId();

        var response = whenCreateANewFish("barracuda", aquariumId, 4);

        assertStatusCode(response, HttpStatus.OK);

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).contains("barracuda");
    }


    @Test
    void given_an_small_aquarium_when_create_a_new_fish_with_3_fins_then_an_error_is_return() {

        var aquariumId = givenAnSmallAquariumId();

        var response = whenCreateANewFish("barracuda", aquariumId, 4);


        assertStatusCode(response, HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("Fish with three fins or more don't go in aquariums of 75 liters or less");

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).doesNotContain("barracuda");
    }

    @Test
    void add_swordfish_not_compatible_with_shark_in_the_same_aquarium_return_error() {

        var aquariumId = givenAnAquariumId();

        var response = whenCreateANewFishWithNoCompatible("swordfish", aquariumId, List.of("shark"));

        assertStatusCode(response, HttpStatus.OK);

        var responseShark = whenCreateANewFish("shark", aquariumId, 2);


        assertStatusCode(responseShark, HttpStatus.BAD_REQUEST);

        assertThat(responseShark.getBody())
                .contains("shark can't go in the same aquarium as [swordfish]");

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).doesNotContain("shark");
    }



}
