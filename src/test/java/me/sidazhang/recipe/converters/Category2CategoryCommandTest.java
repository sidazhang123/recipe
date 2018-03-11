package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.CategoryCommand;
import me.sidazhang.recipe.models.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Category2CategoryCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "descript";
    Category2CategoryCommand convter;

    @Before
    public void setUp() throws Exception {
        convter = new Category2CategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(convter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
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