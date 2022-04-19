package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.dto.RecipeShowDTO;
import com.dbccompany.trabalhofinalmod5.dto.RecipeUpdateDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    public String saveRecipe(RecipeDTO recipe) throws PriceExpensiveException, CaloriesLimitExceededException, IllegalAccessException, UserDontExistException {
        if (recipe.getPrice() > 300) {
            throw new PriceExpensiveException("Price too much expensive!");
        }
        if (recipe.getCalories() > 1500) {
            throw new CaloriesLimitExceededException("Food too much fat!");
        }
        UserEntity user = userRepository.findByUsername(recipe.getAuthor());
        if (user == null) {
            throw new UserDontExistException("User don't exists!");
        }
        if (!user.isIsactive()) {
            throw new IllegalAccessException("User not active!");
        }
        return recipeRepository.saveRecipe(objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void updateRecipe(String hexId, RecipeUpdateDTO recipe) throws RecipeNotFoundException {
        RecipeEntity recipeEntity = findById(hexId);
        RecipeEntity recipePersist = objectMapper.convertValue(recipe, RecipeEntity.class);
        recipePersist.setAuthor(recipeEntity.getAuthor());
        recipeRepository.updateRecipe(hexId, recipePersist);
    }

    public void updateClassifications(String hexId, RecipeShowDTO updateDTO) {
        recipeRepository.updateClassifications(hexId, updateDTO);
    }

    public void deleteRecipe(String hexId) {
        recipeRepository.deleteRecipe(hexId);
    }

    public RecipeShowDTO findByRecipeName(String recipeName) throws RecipeNotFoundException {
        return objectMapper.convertValue(recipeRepository.findByRecipeName(recipeName), RecipeShowDTO.class);
    }

    public RecipeEntity findById(String hexId) throws RecipeNotFoundException {
        RecipeEntity recipeEntity = recipeRepository.findById(hexId);
        if (recipeEntity == null) {
            throw new RecipeNotFoundException("Recipe not found!");
        }
        return recipeEntity;
    }

    public void deleteClassification(String hexId, String objectIdRecipe) {
        UserEntity user = userRepository.findById(hexId);
        recipeRepository.deleteClassification(user.getUsername(), objectIdRecipe);
    }
}
