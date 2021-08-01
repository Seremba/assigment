package com.saburto.petfishstore.repositories;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.Fish;

import org.springframework.jdbc.core.RowMapper;

public class FishRowMapper implements RowMapper<Fish> {

    @Override
    public Fish mapRow(ResultSet rs, int rowNum) throws SQLException {

        var specie = rs.getString("specie");
        var fins = rs.getInt("fins");
        var stock = rs.getInt("stock");
        var color = rs.getString("color");
        var aquariumId = rs.getString("aquarium_id");
        var noCompatibles = rs.getString("no_compatible");

        return Fish.builder()
            .specie(specie)
            .fins(fins)
            .stock(stock)
            .noCompatibleSpecies(convertToSet(noCompatibles))
            .aquariumId(aquariumId)
            .color(Colors.valueOf(color))
            .build();
    }

    private Set<String> convertToSet(String noCompatibles) {

        if (isEmpty(noCompatibles)) {
            return Set.of();
        }
        var output = new HashSet<String>();
        var split = noCompatibles.split(",");
        for(var fish : split) {
            output.add(fish);
        }
        return output;
    }

}
