package org.reseaux.carLoc.dto;

import lombok.Data;

@Data
public class PosteImageDTO {
    private Long imageId;
    private long posteId; ;
    private String name;
    private String type;
    private byte[] imageData;
}
