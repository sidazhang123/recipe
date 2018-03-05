package me.sidazhang.recipe.repositories;

import me.sidazhang.recipe.models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
