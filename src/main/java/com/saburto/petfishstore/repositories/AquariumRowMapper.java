package com.saburto.petfishstore.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.GlassType;
import com.saburto.petfishstore.domain.model.Shape;
import com.saburto.petfishstore.domain.model.Size;

import org.springframework.jdbc.core.RowMapper;

public class AquariumRowMapper implements RowMapper<Aquarium> {

    @Override
    public Aquarium mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var glassType = rs.getString("glass_type");
        var shape = rs.getString("shape");
        var size = rs.getInt("size_liters");

        return Aquarium.builder()
            .id(id)
            .glassType(GlassType.valueOf(glassType))
            .size(Size.ofLiters(size))
            .shape(Shape.valueOf(shape))
            .build();
    }


}
