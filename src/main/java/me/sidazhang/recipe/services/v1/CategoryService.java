package me.sidazhang.recipe.services.v1;


import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
