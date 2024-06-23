package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class ImageVehiculeDTO {
    private Long imageId;
    private String vehiculeImmatriculation;
    private byte[] imageData;
}
