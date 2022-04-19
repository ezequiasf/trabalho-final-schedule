package com.dbccompany.kafkareceita.controller;

import com.dbccompany.kafkareceita.dataTransfer.RecipeCreateDTO;
import com.dbccompany.kafkareceita.dataTransfer.RecipeFormedDTO;
import com.dbccompany.kafkareceita.exceptions.ObjectNotFoundException;
import com.dbccompany.kafkareceita.exceptions.UserNotActiveException;
import com.dbccompany.kafkareceita.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @ApiOperation(value = "Retorna uma lista de receitas registradas.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "As receitas foram listadas com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @GetMapping("/readAllRecipes")
    public List<RecipeFormedDTO> readAllRecipes() throws JsonProcessingException {
        return recipeService.readAllRecipes();
    }

    @ApiOperation(value = "Encontra uma receita registrada através do id informado.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A receita foi informada com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @GetMapping("/{idRecipe}")
    public RecipeFormedDTO findRecipeById(@PathVariable("idRecipe") String idRecipe) throws ObjectNotFoundException, JsonProcessingException {
        return recipeService.findRecipeById(idRecipe);
    }

    @ApiOperation(value = "Cadastra uma receita no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Cadastrou a receita com sucesso no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @PostMapping("/saveRecipe/{idUser}")
    @Validated
    public RecipeFormedDTO saveRecipe(@Valid @RequestBody RecipeCreateDTO recipeCreateDTO, @PathVariable("idUser") String idUser) throws ObjectNotFoundException, UserNotActiveException, JsonProcessingException {
        return recipeService.saveRecipe(recipeCreateDTO, idUser);
    }

    @ApiOperation(value = "Atualiza uma receita no banco de dados através do id informado.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Atualizou com sucesso a receita no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @PutMapping("/updateRecipe/{idRecipe}")
    @Validated
    public RecipeFormedDTO updateRecipe(@PathVariable("idRecipe") String idRecipe,
                                        @Valid @RequestBody RecipeCreateDTO updateRecipe) throws ObjectNotFoundException, UserNotActiveException, JsonProcessingException {
        return recipeService.updateRecipe(updateRecipe, idRecipe);
    }

    @ApiOperation(value = "Deleta uma receita do banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Deletou a receita com sucesso do banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @DeleteMapping("/deleteRecipe/{idRecipe}")
    public void deleteRecipe(@PathVariable("idRecipe") String idRecipe) throws ObjectNotFoundException, JsonProcessingException {
        recipeService.deleteRecipe(idRecipe);
    }


}
