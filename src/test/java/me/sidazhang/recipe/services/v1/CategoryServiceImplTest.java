package me.sidazhang.recipe.services.v1;


import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;
import me.sidazhang.recipe.api.v1.mapper.CategoryMapper;
import me.sidazhang.recipe.models.Category;
import me.sidazhang.recipe.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {
    public static final String ID = "2";
    public static final String NAME = "Jimmy";
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(3, categoryDTOS.size());

    }

    @Test
    public void getCategoryByName() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setCategoryName(NAME);

        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.of(category));

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());

    }

}