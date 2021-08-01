package com.saburto.petfishstore.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.GlassType;
import com.saburto.petfishstore.domain.model.Shape;
import com.saburto.petfishstore.domain.model.Size;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
public class AquariumRepositoryTest {

    @Autowired
    AquariumRepository repo;

    @Test
    void aquarium_not_found_return_null() {
        assertThat(repo.findById(UUID.randomUUID().toString())).isNull();
    }

    @Test
    void aquarium_found_return_not_null() {

        var id = insertNewAquarium();

        assertThat(repo.findById(id))
            .isNotNull()
            .extracting(Aquarium::getId, Aquarium::getGlassType, Aquarium::getShape, Aquarium::getSize)
            .contains(id, GlassType.STRONG, Shape.RECTANGLE, Size.ofLiters(1));
    }

    @Test
    void return_empty_list_of_empty_aquarium() {

        var id = insertNewAquarium();

        assertThat(repo.findSpeciesByAquariumId(id)).isEmpty();
    }

    @Test
    void test() {
        var aq = repo.allAquariums();

        for(var a : aq) {
            var fish = repo.findSpeciesByAquariumId(a.getId());

            System.out.println(fish);
        }
    }

    @Test
    void return_specie_list_of_not_empty_aquarium() {

        var id = insertNewAquarium();

        repo.insertFish("octopus", 2, Colors.GOLDEN, 12, id);

        var species = repo.findSpeciesByAquariumId(id);
        assertThat(species).isNotEmpty();

        var octopus = species.stream().findFirst().get();

        assertThat(octopus.getAquariumId()).isEqualTo(id);
        assertThat(octopus.getColor()).isEqualTo(Colors.GOLDEN);
        assertThat(octopus.getSpecie()).isEqualTo("octopus");
        assertThat(octopus.getFins()).isEqualTo(2);
        assertThat(octopus.getStock()).isEqualTo(12);

    }

    private String insertNewAquarium() {
        var id = UUID.randomUUID().toString();

        repo.insert(id, GlassType.STRONG.name(), 1, Shape.RECTANGLE.name());
        return id;
    }


}
