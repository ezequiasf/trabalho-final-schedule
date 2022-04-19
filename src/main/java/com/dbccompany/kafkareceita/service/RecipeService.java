package com.dbccompany.kafkareceita.service;


import com.dbccompany.kafkareceita.dataTransfer.*;
import com.dbccompany.kafkareceita.entity.RecipeEntity;
import com.dbccompany.kafkareceita.entity.UserEntity;
import com.dbccompany.kafkareceita.exceptions.ObjectNotFoundException;
import com.dbccompany.kafkareceita.exceptions.UserNotActiveException;
import com.dbccompany.kafkareceita.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {
    private final static LogDTO logInfo = new LogDTO();
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final ProducerService producerService;

    public List<RecipeFormedDTO> readAllRecipes() throws JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Encontrar receitas cadastradas."
                , TypeLog.INFO));
        return convertList(recipeRepository.findAll());
    }

    public RecipeFormedDTO findRecipeById(String idRecipe) throws ObjectNotFoundException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Encontrar por id."
                , TypeLog.INFO));
        RecipeEntity r = recipeRepository.findById(idRecipe).orElseThrow(() -> new ObjectNotFoundException("Recipe not found!"));
        return objectMapper.convertValue(r, RecipeFormedDTO.class);
    }

    public RecipeFormedDTO saveRecipe(RecipeCreateDTO recipeCreateDTO, String idUser) throws ObjectNotFoundException, UserNotActiveException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Salvar receitas."
                , TypeLog.INFO));
        UserFormedDTO userFormedDTO = userService.findUserById(idUser);
        UserEntity userEntity = objectMapper.convertValue(userFormedDTO, UserEntity.class);
        if (!userEntity.isActive()) {
            throw new UserNotActiveException("User inactive!");
        }
        RecipeEntity r = objectMapper.convertValue(recipeCreateDTO, RecipeEntity.class);
        log.info("Objeto DTO convertido para tipo Receita.");
        r.setAuthor(userEntity);
        RecipeEntity r2 = recipeRepository.save(r);
        log.info("Receita salva no repositório.");
        return objectMapper.convertValue(r2, RecipeFormedDTO.class);
    }

    public RecipeFormedDTO updateRecipe(RecipeCreateDTO recipeCreateDTO, String idRecipe) throws ObjectNotFoundException, UserNotActiveException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Atualizar receitas."
                , TypeLog.INFO));
        RecipeEntity oldRecipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new ObjectNotFoundException("Recipe not found!"));
        if (!oldRecipe.getAuthor().isActive()) {
            throw new UserNotActiveException("User inactive!");
        }
        RecipeEntity newRecipe = objectMapper.convertValue(recipeCreateDTO, RecipeEntity.class);
        log.info("Objeto DTO convertido para tipo Receita.");
        oldRecipe.setRecipeName(newRecipe.getRecipeName());
        oldRecipe.setAuthor(newRecipe.getAuthor());
        oldRecipe.setIngredients(newRecipe.getIngredients());
        oldRecipe.setPrepareRecipe(newRecipe.getPrepareRecipe());
        oldRecipe.setCalories(newRecipe.getCalories());
        oldRecipe.setPrice(newRecipe.getPrice());
        oldRecipe.setPrepareTime(newRecipe.getPrepareTime());
        log.info("Receita atualizada no repositório.");
        RecipeEntity recipeReturn = recipeRepository.save(oldRecipe);
        return objectMapper.convertValue(recipeReturn, RecipeFormedDTO.class);
    }

    public void deleteRecipe(String idRecipe) throws ObjectNotFoundException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Deletar receitas."
                , TypeLog.INFO));
        RecipeEntity recipeEntity = recipeRepository.findById(idRecipe).orElseThrow(() ->
                new ObjectNotFoundException("Recipe not found!"));
        recipeRepository.delete(recipeEntity);
    }

    private List<RecipeFormedDTO> convertList(List<RecipeEntity> recipes) {
        log.info("Iniciando conversão de lista...");
        return recipes
                .stream()
                .map(r -> objectMapper.convertValue(r, RecipeFormedDTO.class))
                .collect(Collectors.toList());
    }
}
