package me.sidazhang.recipe.controllers.api.v1;

import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;
import me.sidazhang.recipe.api.v1.DTO.CategoryListDTO;
import me.sidazhang.recipe.services.v1.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/category", "/api/v1/category/"})
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);

    }
}
