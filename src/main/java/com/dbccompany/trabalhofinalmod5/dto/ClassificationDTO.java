package com.dbccompany.trabalhofinalmod5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationDTO {
    private String objectIdRecipe;
    private Double rating;
    private String coment;
}
