package me.sidazhang.recipe.api.v1.mapper;

import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;
import me.sidazhang.recipe.models.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {
    public static final String NAME = "Joe";
    public static final String ID = "1";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {

        //given
        Category category = new Category();
        category.setCategoryName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }

}