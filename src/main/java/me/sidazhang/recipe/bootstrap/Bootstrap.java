package me.sidazhang.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.models.*;
import me.sidazhang.recipe.repositories.CategoryRepository;
import me.sidazhang.recipe.repositories.RecipeRepository;
import me.sidazhang.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     UnitOfMeasureRepository unitOfMeasureRepository,
                     RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadCategories();
        loadUom();
        log.warn("loading springboot data");
        recipeRepository.saveAll(setValues());
    }

    private void loadCategories() {
        categoryRepository.deleteAll();
        Category cat1 = new Category();
        cat1.setCategoryName("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setCategoryName("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setCategoryName("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setCategoryName("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom() {
        unitOfMeasureRepository.deleteAll();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        unitOfMeasureRepository.save(uom3);


    }

    private ArrayList<Recipe> setValues() {
        recipeRepository.deleteAll();
        ArrayList<Recipe> arrayList = new ArrayList<>();
        Optional<Category> category = categoryRepository.findByCategoryName("Mexican");
        Optional<Category> category1 = categoryRepository.findByCategoryName("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        Recipe recipe = new Recipe();
        recipe.setCookTime(2);
        recipe.setDescription("This is an amazing dish");
        recipe.setPrepTime(3);
        recipe.getIngredients().add(new Ingredient("chili", new BigDecimal(5), unitOfMeasure.get()));
        recipe.getIngredients().add(new Ingredient("butter", new BigDecimal(5), unitOfMeasure.get()));
        recipe.getIngredients().add(new Ingredient("vinegar", new BigDecimal(5), unitOfMeasure.get()));

//        recipe.addIngredient(new Ingredient("chili", new BigDecimal(5), unitOfMeasure.get()));
//        recipe.addIngredient(new Ingredient("butter", new BigDecimal(5), unitOfMeasure.get()));
//        recipe.addIngredient(new Ingredient("vinegar", new BigDecimal(5), unitOfMeasure.get()));
        recipe.getCategories().add(category.get());
        recipe.getCategories().add(category1.get());
//        recipe.addCategory(category.get());
//        recipe.addCategory(category1.get());
        Notes notes = new Notes();
//        notes.setRecipe(recipe);
        notes.setRecipeNotes("Do not overly cook");
        recipe.setNotes(notes);
        recipe.setDirection("first boil some water ");
        recipe.setDifficulty(Difficulty.HARD);
        recipe.setServings(2);
        recipe.setSource("Asia");
        recipe.setUrl("http://");
        arrayList.add(recipe);
        return arrayList;
    }
}
