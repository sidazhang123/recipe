package me.sidazhang.recipe.repositories.reactive;

import me.sidazhang.recipe.models.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
