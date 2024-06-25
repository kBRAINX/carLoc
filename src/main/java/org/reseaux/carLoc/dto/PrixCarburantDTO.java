package org.reseaux.carLoc.dto;

import lombok.Data;
import org.reseaux.carLoc.models.options.Carburant;

@Data
public class PrixCarburantDTO {
    private long id;
    private Carburant carburant;
    private double price;
}
