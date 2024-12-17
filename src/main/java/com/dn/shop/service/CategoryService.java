package com.dn.shop.service;

import com.dn.shop.model.entity.Category;
import com.dn.shop.repository.CategoryRepository;
import com.dn.shop.exception.CategoryAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.dn.shop.model.dto.category.CategoryDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves all categories from the database
     * @return List of all categories
     */
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll().stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .collect(Collectors.toList());
    }

    /**
     * Creates a new category
     * @param category the category to be created
     * @return the created category
     * @throws CategoryAlreadyExistsException if a category with the same name already exists
     * @throws IllegalArgumentException if the category or category name is null/empty
     */
    public Category createCategory(Category category) {
        if (category == null || !StringUtils.hasText(category.getName())) {
            throw new IllegalArgumentException("Category and category name must not be null or empty");
        }

        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException("Category with name " + category.getName() + " already exists");
        }

        log.debug("Creating new category: {}", category.getName());
        return categoryRepository.save(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
} 