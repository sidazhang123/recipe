package me.sidazhang.recipe.api.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListDTO {


    private List<CategoryDTO> categories;
}
