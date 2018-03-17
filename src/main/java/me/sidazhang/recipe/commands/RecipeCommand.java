package me.sidazhang.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.sidazhang.recipe.models.Difficulty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private String id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;
    @URL
    private String url;
    @NotBlank
    private String direction;
    private Difficulty difficulty;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private NotesCommand notes;
    private List<CategoryCommand> categories = new ArrayList<>();
    private Byte[] image;

}
