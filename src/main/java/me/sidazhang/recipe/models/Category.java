package me.sidazhang.recipe.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@Setter
@Document
public class Category {
    private String id;
    private String categoryName;
    @DBRef
    private Set<Recipe> recipes;
}
