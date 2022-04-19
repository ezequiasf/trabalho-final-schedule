package com.dbccompany.trabalhofinalmod5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeUpdateDTO {
    @NotBlank(message = "O nome da receita deve ser informado.")
    @Size(min = 2, max = 40, message = "O nome da receita deve estar entre 2 e 15 caracteres.")
    private String recipeName;

    @NotBlank(message = "O modo de preparo deve ser informado.")
    @Size(min = 1, max = 2500, message = "O modo de preparo deve ter no máximo 2500 caracteres.")
    private String prepareRecipe;

    @NotNull(message = "O tempo de preparo deve ser informado.")
    private Integer prepareTime;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.0", message = "Não é permitido números negativos.")
    private Double price;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.0", message = "Não é permitido números negativos.")
    private Double calories;

    @NotNull
    private List<String> ingredients;
}
