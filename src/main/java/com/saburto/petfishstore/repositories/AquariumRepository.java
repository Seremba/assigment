package com.saburto.petfishstore.repositories;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.saburto.petfishstore.domain.model.Aquarium;
import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.Fish;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface AquariumRepository extends Repository<Aquarium, UUID> {

    @Query(value = "select id, glass_type, size_liters, shape from Aquarium where id = :id",
           rowMapperClass = AquariumRowMapper.class )
    Aquarium findById(@Param("id") UUID id);

    @Modifying
    @Query("insert into Aquarium (id, glass_type, size_liters, shape) values(:id, :glass_type, :size_liters, :shape)")
    void insert(@Param("id") UUID id,
                @Param("glass_type") String glassType,
                @Param("size_liters") int sizeLiters,
                @Param("shape") String shape);


    @Query(rowMapperClass = FishRowMapper.class,
           value = "with cte_no_compatible as (select specie_a, group_concat(specie_b) no_compatible from FISH_NO_COMPATIBLE group by specie_a) select specie, fins, color, stock, aquarium_id, no_compatible from Fish left join cte_no_compatible on specie_a = specie where aquarium_id  = :id")
    Set<Fish> findSpeciesByAquariumId(@Param("id") UUID id);

    @Modifying
    @Query("insert into Fish (specie, fins, color, stock, aquarium_id) values (:specie, :fins, :color, :stock, :aquarium_id)")
    void insertFish(
                    @Param("specie") String specie,
                    @Param("fins") int fins,
                    @Param("color") Colors color,
                    @Param("stock") int stock,
                    @Param("aquarium_id") UUID id);

    @Query(value = "select id, glass_type, size_liters, shape from Aquarium",
           rowMapperClass = AquariumRowMapper.class)
    List<Aquarium> allAquariums();


}
