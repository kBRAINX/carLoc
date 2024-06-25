package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class ImageVehiculeDTO {
    private Long imageId;
    private String vehiculeImmatriculation;
    private String name;
    private String type;
    private byte[] imageData;
}
