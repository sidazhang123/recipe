package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.converters.UnitOfMeasure2UnitOfMeasureCommand;
import me.sidazhang.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                    UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureRepository;
        this.unitOfMeasure2UnitOfMeasureCommand = unitOfMeasure2UnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return unitOfMeasureReactiveRepository
                .findAll().
                        map(unitOfMeasure2UnitOfMeasureCommand::convert);
    }
}
