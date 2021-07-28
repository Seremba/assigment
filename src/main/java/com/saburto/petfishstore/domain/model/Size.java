package com.saburto.petfishstore.domain.model;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Value;

@Value
public class Size {

    private static final double LITERS_IN_GALLONS = 0.264172;
    @JsonView(Views.Public.class)
    private int liters;

    public static Size ofLiters(int liters) {
        return new Size(liters);
    }

    @JsonView(Views.Public.class)
    public double getGallons() {
        return liters * LITERS_IN_GALLONS;
    }

}
