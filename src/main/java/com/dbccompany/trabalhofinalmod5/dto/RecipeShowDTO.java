package com.dbccompany.trabalhofinalmod5.dto;

import com.dbccompany.trabalhofinalmod5.entity.Classification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeShowDTO extends RecipeDTO {
    private List<Classification> classifications;
}
