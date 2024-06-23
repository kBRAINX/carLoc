package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class PriceVehiculeDTO {
    private long id;
    private String vehiculeImmatriculation;
    private double priceH;
    private double priceJ;
}
