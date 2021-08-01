package com.saburto.petfishstore;

import static org.assertj.core.api.Assertions.assertThat;

import com.saburto.petfishstore.api.AquariumController;
import com.saburto.petfishstore.api.FishController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetFishStoreApplicationTests {

    @Autowired
    private FishController fishController;

    @Autowired
    private AquariumController aquariumController;

    @Test
    void context_load_successfully() {

        assertThat(fishController).isNotNull();
        assertThat(aquariumController).isNotNull();

    }

}
