package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.CategoryCommand;
import me.sidazhang.recipe.models.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Category2CategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if (category == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(category.getId());
        categoryCommand.setCategoryName(category.getCategoryName());

        return categoryCommand;
    }
}