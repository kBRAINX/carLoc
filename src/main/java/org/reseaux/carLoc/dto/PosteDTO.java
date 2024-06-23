package org.reseaux.carLoc.dto;

import lombok.Data;
import org.reseaux.carLoc.models.options.Siege;

@Data
public class PosteDTO {
    private long id;
    private long categoryId;
    private String name;
    private String localisation;
    private Siege siege;
}
