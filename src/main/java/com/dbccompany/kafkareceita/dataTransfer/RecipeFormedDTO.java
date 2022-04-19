package com.dbccompany.kafkareceita.dataTransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeFormedDTO extends RecipeCreateDTO {
    private String objectId;
}
