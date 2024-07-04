package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.CategoryDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Category;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.services.CategoryService;
import org.reseaux.carLoc.services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    private final VehiculeService vehiculeService;

    @Autowired
    public CategoryController(CategoryService categoryService, VehiculeService vehiculeService) {
        this.categoryService = categoryService;
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findOne(@PathVariable("id") long categoryId) {
        Optional<Category> category = categoryService.findOne(categoryId);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/vehicules")
    public ResponseEntity<List<Vehicule>> getVehiculesByCategoryId(@PathVariable("id") long categoryId) {
        List<Vehicule> vehicule = vehiculeService.findByCategoryId(categoryId);
        return new ResponseEntity<>(vehicule, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO categoryDTO) {
        Category createdCategory = categoryService.create(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") long categoryId, @RequestBody CategoryDTO categoryDTO) {
        try {
            Category updatedCategory = categoryService.update(categoryId, categoryDTO);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long categoryId) {
        try {
            categoryService.delete(categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
