package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.CategoryCommand;
import me.sidazhang.recipe.models.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommand2Category implements Converter<CategoryCommand, Category> {
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }
        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setCategoryName(categoryCommand.getCategoryName());
        return category;
    }
}
