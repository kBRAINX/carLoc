package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class NoteChauffeurDTO {
    private long id;
    private long chauffeurId;
    private long clientId;
    private int note;
}
