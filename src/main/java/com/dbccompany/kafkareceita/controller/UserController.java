package com.dbccompany.kafkareceita.controller;


import com.dbccompany.kafkareceita.dataTransfer.UserCreateDTO;
import com.dbccompany.kafkareceita.dataTransfer.UserFormedDTO;
import com.dbccompany.kafkareceita.dataTransfer.UserUpdateDTO;
import com.dbccompany.kafkareceita.exceptions.ObjectNotFoundException;
import com.dbccompany.kafkareceita.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService serviceUsuario;

    @ApiOperation(value = "Retorna uma lista de usuários registrados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Os usuários foram listadas com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @GetMapping("/readAllUsers")
    public List<UserFormedDTO> readAllUsers() throws JsonProcessingException {
        return serviceUsuario.readAllUsers();
    }

    @ApiOperation(value = "Encontra um usuário registrado através do id informado.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi informado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @GetMapping("/{idUser}")
    public UserFormedDTO findUserById(@PathVariable("idUser") String objectId) throws ObjectNotFoundException, JsonProcessingException {
        return serviceUsuario.findUserById(objectId);
    }

    @ApiOperation(value = "Cadastra um usuário no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Cadastrou o usuário com sucesso no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @PostMapping("/saveUser")
    @Validated
    public UserFormedDTO saveUser(@Valid @RequestBody UserCreateDTO userCreateDTO) throws JsonProcessingException {
        return serviceUsuario.saveUser(userCreateDTO);
    }

    @ApiOperation(value = "Atualiza um usuário no banco de dados através do id informado.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Atualizou com sucesso o usuário no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @PutMapping("/updateUser/{idUser}")
    @Validated
    public UserFormedDTO updateUser(@PathVariable("idUser") String idUser,
                                    @Valid @RequestBody UserUpdateDTO userUpdateDTO) throws ObjectNotFoundException, JsonProcessingException {
        return serviceUsuario.updateUser(userUpdateDTO, idUser);
    }

    @ApiOperation(value = "Deleta um usuário do banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Deletou o usuáro com sucesso do banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @DeleteMapping("/deleteUser/{idUser}")
    public void deleteUser(@PathVariable("idUser") String idUser) throws ObjectNotFoundException, JsonProcessingException {
        serviceUsuario.deleteUser(idUser);
    }
}
