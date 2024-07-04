package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.CategoryDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Category;
import org.reseaux.carLoc.repositories.CategoryRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private  CassandraIdGenerator cassandraIdGenerator;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByAgenceId(long agenceId) {
        return categoryRepository.findByAgenceId(agenceId);
    }

    public Optional<Category> findOne(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category create(CategoryDTO categoryDTO) {
        Category category = new Category();
        Long newId = cassandraIdGenerator.getNextId("categories");
        category.setId(newId);
        category.setAgenceId(categoryDTO.getAgenceId());
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    public Category update(long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDTO.getName());
            return categoryRepository.save(category);
        } else {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
    }

    public void delete(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
