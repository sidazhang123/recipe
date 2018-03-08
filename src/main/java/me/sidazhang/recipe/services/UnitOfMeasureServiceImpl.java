package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.converters.UnitOfMeasure2UnitOfMeasureCommand;
import me.sidazhang.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasure2UnitOfMeasureCommand = unitOfMeasure2UnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasure2UnitOfMeasureCommand::convert).collect(Collectors.toSet());
    }
}
