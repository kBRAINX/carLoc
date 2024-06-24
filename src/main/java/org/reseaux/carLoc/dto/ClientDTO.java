package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private String sexe;
    private String phone;
}
