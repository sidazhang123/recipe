package me.sidazhang.recipe.repositories;

import me.sidazhang.recipe.models.Recipe;
import org.springframework.data.repository.CrudRepository;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
