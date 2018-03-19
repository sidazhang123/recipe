package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.converters.UnitOfMeasure2UnitOfMeasureCommand;
import me.sidazhang.recipe.models.UnitOfMeasure;
import me.sidazhang.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand = new UnitOfMeasure2UnitOfMeasureCommand();
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasure2UnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() {
        //given

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");


        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");


        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        //when
        List<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms().collectList().block();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();
    }

}