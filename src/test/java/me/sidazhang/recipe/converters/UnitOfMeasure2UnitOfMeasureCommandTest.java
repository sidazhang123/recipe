package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jt on 6/21/17.
 */
public class UnitOfMeasure2UnitOfMeasureCommandTest {

    private static final String DESCRIPTION = "description";
    private static final String String_VALUE = "1";

    private UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand;

    @Before
    public void setUp() {
        unitOfMeasure2UnitOfMeasureCommand = new UnitOfMeasure2UnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() {
        assertNull(unitOfMeasure2UnitOfMeasureCommand.convert(null));
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(unitOfMeasure2UnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(String_VALUE);
        uom.setUom(DESCRIPTION);
        //when
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasure2UnitOfMeasureCommand.convert(uom);

        //then
        assertEquals(String_VALUE, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getUom());
    }

}