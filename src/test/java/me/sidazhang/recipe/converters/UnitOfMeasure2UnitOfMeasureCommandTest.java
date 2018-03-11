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

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand;

    @Before
    public void setUp() throws Exception {
        unitOfMeasure2UnitOfMeasureCommand = new UnitOfMeasure2UnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(unitOfMeasure2UnitOfMeasureCommand.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(unitOfMeasure2UnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setUom(DESCRIPTION);
        //when
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasure2UnitOfMeasureCommand.convert(uom);

        //then
        assertEquals(LONG_VALUE, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getUom());
    }

}