package me.sidazhang.recipe.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class Customer {

    private Long id;
    private String firstname;
    private String lastname;
}
