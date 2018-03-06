package me.sidazhang.recipe.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }
    @Test
    public void getId() {
        Long l = 4L;
        category.setId(l);
        assertEquals(l, category.getId());
    }

    @Test
    public void getCategoryName() {
    }

    @Test
    public void getRecipes() {
    }
}