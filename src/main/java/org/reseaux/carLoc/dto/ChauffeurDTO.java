package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class ChauffeurDTO {
    private long id;
    private long agenceId;
    private long syndicatId;
    private String name;
    private String email;
    private String city;
    private String phoneNumber;
}
