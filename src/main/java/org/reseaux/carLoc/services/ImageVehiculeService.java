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
import java.util.List;
import java.util.Optional;

@Service
public class ImageVehiculeService {

    @Autowired
    private ImageVehiculeRepository imageVehiculeRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;
    @Autowired
    private VehiculeRepository vehiculeRepository;

    public ImageVehicule uploadImage(String vehiculeImmatriculation, MultipartFile file) throws IOException {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(vehiculeImmatriculation);
        if (optionalVehicule.isPresent()) {
            Vehicule vehicule = optionalVehicule.get();
            ImageVehicule imageVehicule = new ImageVehicule();
            long newId = cassandraIdGenerator.getNextId("image_vehicule");
            imageVehicule.setId(newId);
            imageVehicule.setVehiculeImmatriculation(vehicule.getImmatriculation());
            imageVehicule.setImageData(file.getBytes());
            return  imageVehiculeRepository.save(imageVehicule);
        } else {
            throw new IOException("Vehicule not found");
        }
    }

    public List<ImageVehicule> getImages(String vehiculeImmatriculation) {
        return imageVehiculeRepository.findByVehiculeImmatriculation(vehiculeImmatriculation);
    }


    public ImageVehicule update(Long imageId, MultipartFile file) throws IOException {
        Optional<ImageVehicule> optionalImageVehicule = imageVehiculeRepository.findById(imageId);
        if (optionalImageVehicule.isPresent()) {
            ImageVehicule imageVehicule = optionalImageVehicule.get();
            imageVehicule.setImageData(file.getBytes());
            return imageVehiculeRepository.save(imageVehicule);
        } else {
            throw new IOException("Image not found");
        }
    }

    public void delete(Long imageId) {
        imageVehiculeRepository.deleteById(imageId);
    }
}
