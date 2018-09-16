package me.sidazhang.recipe.api.v1.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @ApiModelProperty(value = "first name", required = true)
    private String firstname;
    private String lastname;
    private String url;
}
