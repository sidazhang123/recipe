package me.sidazhang.recipe.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public Category() {
    }


}
