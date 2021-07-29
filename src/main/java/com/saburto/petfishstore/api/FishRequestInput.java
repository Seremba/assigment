package com.saburto.petfishstore.api;

import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.UpdateFishRequest;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Valid
public class FishRequestInput implements UpdateFishRequest {

    @NotBlank
    private String specie;

    @NotNull(groups = Validate.OnCreate.class)
    private Colors color;

    @Min(value = 0, groups = Validate.OnCreate.class)
    private Integer fins;

    @Min(value = 0, groups = Validate.OnCreate.class)
    private Integer stock;

    @NotBlank(groups = Validate.OnCreate.class)
    private String aquariumId;

    @Singular("noCompatibleSpecie")
    private Set<String> noCompatibleSpecies;

    @Override
    public UUID getAquariumID() {
        return UUID.fromString(aquariumId);
    }


}
