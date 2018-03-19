package me.sidazhang.recipe.repositories.reactive;

import me.sidazhang.recipe.models.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findByCategoryName(String categoryName);
}
