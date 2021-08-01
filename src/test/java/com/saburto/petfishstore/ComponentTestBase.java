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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class ComponentTestBase {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String url;

    @BeforeEach
    void setup() {
        url = String.format("http://localhost:%s", port);
    }

    protected Set<String> thenRetrieveFishesFromAquarium(String aquariumId) {

        var aquarium = restTemplate.getForEntity(url + "/aquariums/" + aquariumId + "/fishes", JsonNode.class);
        return StreamSupport.stream(aquarium.getBody().spliterator(), false)
            .map(n -> n.get("specie").asText())
            .collect(toSet());
    }

    protected ResponseEntity<String> whenCreateANewFishWithNoCompatible(String specie, String aquariumId, List<String> noCompatible) {
        return whenCreateANewFish(specie, aquariumId, 2, noCompatible, 12);
    }

    protected ResponseEntity<String> whenCreateANewFishWithStock(String specie, String aquariumId,
            int stock) {
        return whenCreateANewFish(specie, aquariumId, 2, List.of(), stock);
    }

    protected ResponseEntity<String> whenCreateANewFish(String specie, String aquariumId, int fins) {
        return whenCreateANewFish(specie, aquariumId, fins, List.of(), 12);
    }

    protected ResponseEntity<String> whenCreateANewFish(String specie, String aquariumId, int fins, List<String> noCompatible, int stock) {

        var request = Map.of("specie", specie,
                             "color", "BLUE",
                             "fins", fins,
                             "stock", stock,
                             "noCompatibleSpecies", noCompatible,
                             "aquariumId", aquariumId);

        return restTemplate.postForEntity(url + "/fish", request, String.class);
    }

    protected String givenAnAquariumId() {
        return givenAnAquarium(size -> size >= 75);
    }

    protected ResponseEntity<JsonNode> allAquariums() {
        return restTemplate.getForEntity(url + "/aquariums", JsonNode.class);
    }

    protected String givenAnSmallAquariumId() {
        return givenAnAquarium(size -> size <= 75);
    }

    protected String givenAnAquarium(Predicate<Integer> filter) {
        return StreamSupport.stream(allAquariums().getBody().spliterator(), false)
            .filter(n -> filter.test(n.get("size").get("liters").asInt()))
            .map(n -> n.get("id").asText())
            .findFirst()
            .orElseThrow();
    }

    protected void assertStatusCode(ResponseEntity<String> response, HttpStatus httpStatus) {
        assertThat(response.getStatusCode())
                .as("checking response: %s, body: %s", response.toString(), response.getBody()).isEqualTo(httpStatus);
    }
}
