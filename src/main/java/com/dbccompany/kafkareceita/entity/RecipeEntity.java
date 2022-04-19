package com.dbccompany.kafkareceita.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "recipes")
public class RecipeEntity {
    @Id
    private String objectId;
    @DBRef
    private UserEntity author;
    private String recipeName;
    private String prepareRecipe;
    private Integer prepareTime;
    private Double price;
    private Double calories;
    private List<String> ingredients;
}
