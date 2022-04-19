package com.dbccompany.trabalhofinalmod5.controller;


import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.dto.RecipeShowDTO;
import com.dbccompany.trabalhofinalmod5.dto.RecipeUpdateDTO;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @ApiOperation(value = "Registra uma receita no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A receita foi registrada com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PostMapping("/saveRecipe")
    @Validated
    public String saveRecipe(@Valid @RequestBody RecipeDTO recipe) throws PriceExpensiveException, CaloriesLimitExceededException, IllegalAccessException, UserDontExistException {
        return recipeService.saveRecipe(recipe);
    }

    @ApiOperation(value = "Atualiza uma receita no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A receita foi atualizada com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PutMapping("/updateRecipe")
    @Validated
    public void updateRecipe(@RequestParam("hexId") String hexId,
                             @Valid @RequestBody RecipeUpdateDTO recipe) throws RecipeNotFoundException {
        recipeService.updateRecipe(hexId, recipe);
    }

    @ApiOperation(value = "Deleta uma receita do banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A receita foi deletada com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @DeleteMapping("/deleteRecipe")
    public void deleteRecipe(@RequestParam("hexId") String hexId) {
        recipeService.deleteRecipe(hexId);
    }

    @ApiOperation(value = "Encontrar receitas pelo nome.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "As receitas foram listadas com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @GetMapping("/findByRecipeName")
    public RecipeShowDTO findByRecipeName(@RequestParam("recipeName") String recipeName) throws RecipeNotFoundException {
        return recipeService.findByRecipeName(recipeName);
    }
}
