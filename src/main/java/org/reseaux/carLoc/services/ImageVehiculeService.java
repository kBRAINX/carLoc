package org.reseaux.carLoc.services;

import org.reseaux.carLoc.models.ImageVehicule;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.repositories.ImageVehiculeRepository;
import org.reseaux.carLoc.repositories.VehiculeRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageVehiculeService {

    private final ImageVehiculeRepository imageVehiculeRepository;
    private final CassandraIdGenerator cassandraIdGenerator;
    private final VehiculeRepository vehiculeRepository;

    @Autowired
    public ImageVehiculeService(ImageVehiculeRepository imageVehiculeRepository, CassandraIdGenerator cassandraIdGenerator, VehiculeRepository vehiculeRepository) {
        this.imageVehiculeRepository = imageVehiculeRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
        this.vehiculeRepository = vehiculeRepository;
    }

    public List<ImageVehicule> uploadImage(String vehiculeImmatriculation, MultipartFile[] files) throws IOException {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(vehiculeImmatriculation);
        if (optionalVehicule.isPresent()) {
            Vehicule vehicule = optionalVehicule.get();
            List<ImageVehicule> savedImages = new ArrayList<>();

            for (MultipartFile file : files) {
                ImageVehicule imageVehicule = new ImageVehicule();
                long newId = cassandraIdGenerator.getNextId("image_vehicule");
                imageVehicule.setId(newId);
                imageVehicule.setVehiculeImmatriculation(vehicule.getImmatriculation());

                var imageToSave = ImageVehicule.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(file.getBytes())
                    .build();

                imageVehicule.setName(imageToSave.getName());
                imageVehicule.setType(imageToSave.getType());
                imageVehicule.setImageData(imageToSave.getImageData());

                ImageVehicule savedImage = imageVehiculeRepository.save(imageVehicule);
                savedImages.add(savedImage);
            }

            return savedImages;
        } else {
            throw new IOException("Vehicule not found");
        }
    }

    public byte[] downloadImage(String imageName) {
        Optional<ImageVehicule> dbImage = imageVehiculeRepository.findByName(imageName);
        return dbImage.map(ImageVehicule::getImageData).orElse(null);
    }

    public List<ImageVehicule> getImages(String vehiculeImmatriculation) {
        return imageVehiculeRepository.findByVehiculeImmatriculation(vehiculeImmatriculation);
    }

    public ImageVehicule update(Long imageId, MultipartFile file) throws IOException {
        Optional<ImageVehicule> optionalImageVehicule = imageVehiculeRepository.findById(imageId);
        if (optionalImageVehicule.isPresent()) {
            ImageVehicule imageVehicule = optionalImageVehicule.get();

            if (file != null && !file.isEmpty()) {
                imageVehicule.setName(file.getOriginalFilename());
                imageVehicule.setType(file.getContentType());
                imageVehicule.setImageData(file.getBytes());
            }

            return imageVehiculeRepository.save(imageVehicule);
        } else {
            throw new IOException("Image not found");
        }
    }

    public void delete(Long imageId) {
        imageVehiculeRepository.deleteById(imageId);
    }
}
