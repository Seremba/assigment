package com.saburto.petfishstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;


public class UpdateFishComponentTest extends ComponentTestBase {


    @Test
    void given_a_tuna_with_stock_1_update_to_stock_100() {

        var tuna = givenAFishWithStock("tuna", 1);

        assertThat(tuna.get("stock").asInt()).isEqualTo(1);

        var response = whenUpdateFishStock("tuna", 100);

        assertStatusCode(response, HttpStatus.OK);

        var tunaUpdated = getFish("tuna");

        assertThat(tunaUpdated.get("stock").asInt()).isEqualTo(100);

        List.of("fins", "aquariumId", "specie", "noCompatible", "color")
            .forEach(f -> assertThat(tunaUpdated.get(f)).isEqualTo(tuna.get(f)));

    }

    @Test
    void move_big_fish_to_a_small_aquarium_should_return_error() {

        var bigAquarium = givenAnAquariumId();
        whenCreateANewFish("salmon", bigAquarium, 5);

        var smallAquarium = givenAnSmallAquariumId();

        var response = whenUpdateFishAquarium("salmon", smallAquarium);

        assertStatusCode(response, HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("Fish with three fins or more don't go in aquariums of 75 liters or less");

    }

    private JsonNode getFish(String string) {
        return restTemplate.getForEntity(url + "/fish/" + string, JsonNode.class).getBody();
    }

    private ResponseEntity<String> whenUpdateFishStock(String specie, int newStock) {
        var request = Map.of("specie", specie,
                             "stock", newStock);

        return whenUpdateFish(request);
    }

    private ResponseEntity<String> whenUpdateFishAquarium(String specie, String aquariumId) {
        var request = Map.of("specie", specie,
                             "aquariumId", aquariumId);

        return whenUpdateFish(request);
    }

    private ResponseEntity<String> whenUpdateFish(Map<String, ? extends Object> request) {
        var rq = RequestEntity.put(url + "/fish").accept(MediaType.APPLICATION_JSON).body(request);
        return restTemplate.exchange(rq, String.class);
    }

    private JsonNode givenAFishWithStock(String fish, int stock) {
        var aquariumId = givenAnAquariumId();
        whenCreateANewFishWithStock(fish, aquariumId, stock);
        return getFish(fish);
    }
}
