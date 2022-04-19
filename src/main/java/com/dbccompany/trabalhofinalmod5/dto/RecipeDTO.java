package com.dbccompany.trabalhofinalmod5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecipeDTO {
    @NotBlank(message = "O nome do autor deve ser informado.")
    @Size(min = 2, max = 40, message = "O nome ddo autor deve estar entre 2 e 15 caracteres.")
    private String author;

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
