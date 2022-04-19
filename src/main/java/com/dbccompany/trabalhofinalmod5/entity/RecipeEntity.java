package com.dbccompany.trabalhofinalmod5.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeEntity {
    private String objectId;
    private String author;
    private String recipeName;
    private String prepareRecipe;
    private Integer prepareTime;
    private Double price;
    private Double calories;
    private List<String> ingredients;
    private List<Classification> classifications;
}
