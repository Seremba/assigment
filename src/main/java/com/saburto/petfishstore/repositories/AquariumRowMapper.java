package com.saburto.petfishstore.repositories;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.GlassType;
import com.saburto.petfishstore.domain.model.Shape;
import com.saburto.petfishstore.domain.model.Size;

import org.springframework.jdbc.core.RowMapper;

public class AquariumRowMapper implements RowMapper<Aquarium> {

    @Override
    public Aquarium mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getBytes("id");
        var glassType = rs.getString("glass_type");
        var shape = rs.getString("shape");
        var size = rs.getInt("size_liters");

        return Aquarium.builder()
            .id(toUUID(id))
            .glassType(GlassType.valueOf(glassType))
            .size(Size.ofLiters(size))
            .shape(Shape.valueOf(shape))
            .build();
    }

    static UUID toUUID(byte[] id) {
        var bb = ByteBuffer.wrap(id);
        return new UUID(bb.getLong(), bb.getLong());
    }

}
