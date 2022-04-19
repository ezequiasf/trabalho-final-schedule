package com.dbccompany.trabalhofinalmod5.controller;

import com.dbccompany.trabalhofinalmod5.dto.ClassificationDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserShowDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserUpdateDTO;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Salva um usuário no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi registrado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PostMapping("/saveUser")
    public String saveUser(@Valid @RequestBody UserDTO user) throws UserAlreadyExistsException, IllegalAccessException {
        return userService.saveUser(user);
    }

    @ApiOperation(value = "Atualiza um usuário no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi atualizado com sucesso no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PutMapping("/updateUser")
    public void updateUser(@RequestParam("hexId") String hexId, @Valid @RequestBody UserUpdateDTO user) throws UserDontExistException {
        userService.updateUser(hexId, user);
    }

    @ApiOperation(value = "Deleta um usuário do banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi deletado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam("hexId") String hexId) {
        userService.deleteUser(hexId);
    }


    @ApiOperation(value = "Retorna um usuário através de seu username.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi listado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @GetMapping("/findByUserName")
    public UserShowDTO findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value = "Posta uma classificação para uma receita.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "A classificação foi postada com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PostMapping("/postClassification/{userObjectId}")
    public void postClassification(@PathVariable("userObjectId") String hexId, @RequestBody ClassificationDTO classificationDTO) throws RecipeNotFoundException, UserDontExistException {
        userService.postClassification(hexId, classificationDTO);
    }

    @DeleteMapping("/deleteClassification/{userObjectId}")
    public void deleteClassification(@PathVariable("userObjectId") String userHexId,
                                     String objectIdRecipe) {
        userService.deleteClassification(userHexId, objectIdRecipe);
    }


}
