package me.sidazhang.recipe.api.v1.mapper;

import me.sidazhang.recipe.api.v1.DTO.CategoryDTO;
import me.sidazhang.recipe.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "id", target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
