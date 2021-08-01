package com.saburto.petfishstore;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateFishComponentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeEach
    void setup() {
        url = String.format("http://localhost:%s", port);
    }

    @Test
    void given_an_aquarium_create_a_new_fish_in_it() {

        var aquariumId = givenAnAquariumId();

        var response = whenCreateANewFish("barracuda", aquariumId, 4);

        assertThat(response.getStatusCode())
                .as("checking response: %s, body: %s", response.toString(), response.getBody())
                .isEqualTo(HttpStatus.OK);

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).contains("barracuda");
    }


    @Test
    void given_an_small_aquarium_when_create_a_new_fish_with_3_fins_then_an_error_is_return() {

        var aquariumId = givenAnSmallAquariumId();

        var response = whenCreateANewFish("barracuda", aquariumId, 4);

        assertThat(response.getStatusCode())
                .as("checking response: %s, body: %s", response.toString(), response.getBody())
            .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody()).contains("Fish with three fins or more don't go in aquariums of 75 liters or less");

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).doesNotContain("barracuda");
    }

    @Test
    void add_swordfish_not_compatible_with_shark_in_the_same_aquarium_return_error() {

        var aquariumId = givenAnAquariumId();

        var response = whenCreateANewFishWithNoCompatible("swordfish", aquariumId, List.of("shark"));

        assertThat(response.getStatusCode())
                .as("checking response: %s, body: %s", response.toString(), response.getBody())
                .isEqualTo(HttpStatus.OK);

        var responseShark = whenCreateANewFish("shark", aquariumId, 2);


        assertThat(responseShark.getStatusCode())
                .as("checking response: %s, body: %s", responseShark.toString(), responseShark.getBody())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseShark.getBody())
                .contains("shark can't go in the same aquarium as [swordfish]");

        var fishes = thenRetrieveFishesFromAquarium(aquariumId);

        assertThat(fishes).doesNotContain("shark");
    }


    private Set<String> thenRetrieveFishesFromAquarium(String aquariumId) {

        var aquarium = restTemplate.getForEntity(url + "/aquariums/" + aquariumId + "/fishes", JsonNode.class);
        return StreamSupport.stream(aquarium.getBody().spliterator(), false)
            .map(n -> n.get("specie").asText())
            .collect(toSet());
    }

    private ResponseEntity<String> whenCreateANewFishWithNoCompatible(String specie, String aquariumId, List<String> noCompatible) {
        return whenCreateANewFish(specie, aquariumId, 2, noCompatible);
    }

    private ResponseEntity<String> whenCreateANewFish(String specie, String aquariumId, int fins) {
        return whenCreateANewFish(specie, aquariumId, fins, List.of());
    }

    private ResponseEntity<String> whenCreateANewFish(String specie, String aquariumId, int fins, List<String> noCompatible) {

        var request = Map.of("specie", specie,
                             "color", "BLUE",
                             "fins", fins,
                             "stock", 12,
                             "noCompatibleSpecies", noCompatible,
                             "aquariumId", aquariumId);

        return restTemplate.postForEntity(url + "/fish", request, String.class);
    }

    private String givenAnAquariumId() {
        return givenAnAquarium(size -> size >= 75);
    }

    private ResponseEntity<JsonNode> allAquariums() {
        return restTemplate.getForEntity(url + "/aquariums", JsonNode.class);
    }

    private String givenAnSmallAquariumId() {
        return givenAnAquarium(size -> size <= 75);
    }

    private String givenAnAquarium(Predicate<Integer> filter) {
        return StreamSupport.stream(allAquariums().getBody().spliterator(), false)
            .filter(n -> filter.test(n.get("size").get("liters").asInt()))
            .map(n -> n.get("id").asText())
            .findFirst()
            .orElseThrow();
    }

}
