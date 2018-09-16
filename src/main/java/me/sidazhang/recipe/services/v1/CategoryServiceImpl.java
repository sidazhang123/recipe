package me.sidazhang.recipe.services.v1;

import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;
import me.sidazhang.recipe.api.v1.mapper.CategoryMapper;
import me.sidazhang.recipe.exceptions.ResourceNotFoundException;
import me.sidazhang.recipe.models.Category;
import me.sidazhang.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryDTO> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByCategoryName(name);
        if (!category.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return categoryMapper.categoryToCategoryDTO(category.get());
    }
}
