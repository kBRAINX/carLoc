package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class PriceChauffeurDTO {
    private long id;
    private long chauffeurId;
    private double priceH;
    private double priceJ;
}
