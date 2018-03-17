package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommand2UnitOfMeasureTest {

    private static final String DESCRIPTION = "description";
    private static final String String_VALUE = "1";

    private UnitOfMeasureCommand2UnitOfMeasure unitOfMeasureCommand2UnitOfMeasure;

    @Before
    public void setUp() {
        unitOfMeasureCommand2UnitOfMeasure = new UnitOfMeasureCommand2UnitOfMeasure();

    }

    @Test
    public void testNullParamter() {
        assertNull(unitOfMeasureCommand2UnitOfMeasure.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(unitOfMeasureCommand2UnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(String_VALUE);
        command.setUom(DESCRIPTION);

        //when
        UnitOfMeasure uom = unitOfMeasureCommand2UnitOfMeasure.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(String_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getUom());

    }

}