package org.reseaux.carLoc.services;

import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.models.PosteImage;
import org.reseaux.carLoc.repositories.PosteImageRepository;
import org.reseaux.carLoc.repositories.PosteRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PosteImageService {
    @Autowired
    private PosteImageRepository posteImageRepository;
    @Autowired
    private PosteRepository posteRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public PosteImage uploadImage(long posteId, MultipartFile file) throws IOException {
        Optional<Poste> optionalPoste = posteRepository.findById(posteId);
        if (optionalPoste.isPresent()) {
            Poste poste = optionalPoste.get();
            PosteImage imagePoste = new PosteImage();
            long newId = cassandraIdGenerator.getNextId("service_image");
            imagePoste.setId(newId);
            imagePoste.setPosteId(poste.getId());
            var imageToSave = PosteImage.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(file.getBytes())
                .build();
            imagePoste.setName(imageToSave.getName());
            imagePoste.setType(imageToSave.getType());
            imagePoste.setImageData(imageToSave.getImageData());
            return  posteImageRepository.save(imagePoste);
        } else {
            throw new IOException("poste not found");
        }
    }

    public byte[] downloadImage(String imageName) {
        Optional<PosteImage> dbImage = posteImageRepository.findByName(imageName);
        return dbImage.map(PosteImage::getImageData).orElse(null);
    }

    public List<PosteImage> getImages(long posteId) {
        return posteImageRepository.findByPosteId(posteId);
    }

    public PosteImage update(Long imageId, MultipartFile file) throws IOException {
        Optional<PosteImage> optionalImagePoste = posteImageRepository.findById(imageId);
        if (optionalImagePoste.isPresent()) {
            PosteImage imagePoste = optionalImagePoste.get();

            if (file != null && !file.isEmpty()) {
                imagePoste.setName(file.getOriginalFilename());
                imagePoste.setType(file.getContentType());
                imagePoste.setImageData(file.getBytes());
            }

            return posteImageRepository.save(imagePoste);
        } else {
            throw new IOException("Image not found");
        }
    }

    public void delete(Long imageId) {
        posteImageRepository.deleteById(imageId);
    }
}
