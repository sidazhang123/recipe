package me.sidazhang.recipe.services;

import me.sidazhang.recipe.models.Recipe;

import java.util.Collection;

public interface RecipeService {
    Collection<Recipe> getRecipes();
}
