package com.saburto.petfishstore.api;

import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.saburto.petfishstore.domain.model.Colors;
import com.saburto.petfishstore.domain.model.NewFishRequest;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@Valid
public class NewFishRequestInput implements NewFishRequest {

    @NotBlank
    private String specie;

    @NonNull
    private Colors color;

    @Min(0)
    private int fins;

    @NotBlank
    private String aquariumId;

    @Singular("noCompatibleSpecie")
    private Set<String> noCompatibleSpecies;

    @Override
    public UUID getAquariumID() {
        return UUID.fromString(aquariumId);
    }


}
