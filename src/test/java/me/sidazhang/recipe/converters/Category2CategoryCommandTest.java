package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.CategoryCommand;
import me.sidazhang.recipe.models.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Category2CategoryCommandTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "descript";
    private Category2CategoryCommand convter;

    @Before
    public void setUp() {
        convter = new Category2CategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(convter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setCategoryName(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getCategoryName());

    }

}