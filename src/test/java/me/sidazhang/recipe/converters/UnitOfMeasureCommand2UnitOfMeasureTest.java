package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommand2UnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasureCommand2UnitOfMeasure unitOfMeasureCommand2UnitOfMeasure;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureCommand2UnitOfMeasure = new UnitOfMeasureCommand2UnitOfMeasure();

    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(unitOfMeasureCommand2UnitOfMeasure.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(unitOfMeasureCommand2UnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setUom(DESCRIPTION);

        //when
        UnitOfMeasure uom = unitOfMeasureCommand2UnitOfMeasure.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getUom());

    }

}