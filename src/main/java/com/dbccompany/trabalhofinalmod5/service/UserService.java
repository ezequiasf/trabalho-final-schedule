package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.*;
import com.dbccompany.trabalhofinalmod5.entity.Classification;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RecipeService recipeService;

    public String saveUser(UserDTO user) throws UserAlreadyExistsException, IllegalAccessException {
        UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

        if (!verifyAge(userEntity)) {
            throw new IllegalAccessException("User too young!");
        }
        if (userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        return userRepository.saveUser(userEntity);
    }

    public void updateUser(String hexId, UserUpdateDTO user) throws UserDontExistException {
        UserEntity userEntity = findById(hexId);
        UserEntity userPersist = objectMapper.convertValue(user, UserEntity.class);
        userPersist.setUsername(userEntity.getUsername());
        userRepository.updateUser(hexId, userPersist);
    }

    public UserShowDTO findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return objectMapper.convertValue(user, UserShowDTO.class);
    }

    public UserEntity findById(String hexId) throws UserDontExistException {
        UserEntity userEntity = userRepository.findById(hexId);
        if (userEntity == null) {
            throw new UserDontExistException("User dont exist!");
        }
        return userEntity;
    }

    public void deleteUser(String hexId) {
        userRepository.deleteUser(hexId);
    }

    //Retorna true se for maior do que 18 anos
    public boolean verifyAge(UserEntity user) {
        return user.getAge() >= 18;
    }

    public void postClassification(String hexId, ClassificationDTO classificationDTO) throws RecipeNotFoundException, UserDontExistException {
        RecipeEntity recipe = recipeService.findById(classificationDTO.getObjectIdRecipe());
        UserEntity user = findById(hexId);

        if (recipe.getClassifications() != null) {
            recipe.getClassifications().add(Classification.builder()
                    .authorClass(user.getUsername())
                    .rating(classificationDTO.getRating())
                    .coment(classificationDTO.getComent()).build());
        } else {
            recipe.setClassifications(List.of(Classification.builder()
                    .authorClass(user.getUsername())
                    .rating(classificationDTO.getRating())
                    .coment(classificationDTO.getComent()).build()));
        }

        RecipeShowDTO recipeUpdate = objectMapper.convertValue(recipe, RecipeShowDTO.class);
        recipeService.updateClassifications(classificationDTO.getObjectIdRecipe(), recipeUpdate);
    }

    public void deleteClassification(String userHexId, String objectIdRecipe) {
        recipeService.deleteClassification(userHexId, objectIdRecipe);
    }
}
