package me.sidazhang.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private String id;
    @NotBlank
    private String description;
    @NotNull
    @Min(1)
    private BigDecimal amount;
    @NotNull
    private UnitOfMeasureCommand uom;
    private String recipeId;

}
