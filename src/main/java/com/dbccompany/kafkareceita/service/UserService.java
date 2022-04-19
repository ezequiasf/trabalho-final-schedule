package com.dbccompany.kafkareceita.service;


import com.dbccompany.kafkareceita.dataTransfer.*;
import com.dbccompany.kafkareceita.entity.UserEntity;
import com.dbccompany.kafkareceita.exceptions.ObjectNotFoundException;
import com.dbccompany.kafkareceita.repository.UserRepository;
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
public class UserService {
    private final static LogDTO logInfo = new LogDTO();
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ProducerService producerService;

    public List<UserFormedDTO> readAllUsers() throws JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Encontrar usuários cadastrados."
                , TypeLog.INFO));
        return convertList(userRepository.findAll());
    }

    public UserFormedDTO findUserById(String objectId) throws ObjectNotFoundException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Encontrar por id."
                , TypeLog.INFO));
        UserEntity u = userRepository.findById(objectId).orElseThrow(() ->
                new ObjectNotFoundException("User not found!"));
        log.info("Feita verificação do ID.");
        return objectMapper.convertValue(u, UserFormedDTO.class);
    }

    public UserFormedDTO saveUser(UserCreateDTO userCreateDTO) throws JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Salvar usuários."
                , TypeLog.INFO));
        UserEntity u = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        log.info("Objeto DTO convertido para tipo Usuario.");
        u.setActive(true);
        UserEntity u2 = userRepository.save(u);
        log.info("Usuário salvo no repositório.");
        return objectMapper.convertValue(u2, UserFormedDTO.class);
    }

    public UserFormedDTO updateUser(UserUpdateDTO userUpdateDTO, String idUser) throws ObjectNotFoundException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Atualizar usuários."
                , TypeLog.INFO));
        UserEntity oldUser = userRepository.findById(idUser)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!"));
        UserEntity newUser = objectMapper.convertValue(userUpdateDTO, UserEntity.class);
        log.info("Objeto DTO convertido para tipo Usuario.");
        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setActive(newUser.isActive());
        userRepository.save(oldUser);
        return objectMapper.convertValue(oldUser, UserFormedDTO.class);
    }

    public void deleteUser(String idUser) throws ObjectNotFoundException, JsonProcessingException {
        producerService.sendMessage(logInfo.constroiLog("Chamada de método service:: Deletar usuários."
                , TypeLog.INFO));
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!"));
        userEntity.setActive(false);
        userRepository.save(userEntity);
        log.info("Usúario desativado.");
    }

    private List<UserFormedDTO> convertList(List<UserEntity> users) {
        log.info("Iniciando conversão de lista...");
        return users
                .stream()
                .map(u -> objectMapper.convertValue(u, UserFormedDTO.class))
                .collect(Collectors.toList());
    }
}
