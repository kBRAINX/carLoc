package org.reseaux.carLoc.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private String sexe;
}

