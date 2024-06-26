package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class NoteVehiculeDTO {
    private long id;
    private long vehiculeId;
    private long clientId;
    private int note;
}
