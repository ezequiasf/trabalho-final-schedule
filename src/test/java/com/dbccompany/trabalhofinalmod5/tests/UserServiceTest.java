package com.dbccompany.trabalhofinalmod5.tests;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(userService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSeUsuarioNaoSeraCadastradoSendoMenorDeIdade() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(17);
        IllegalAccessException exc = Assertions.assertThrows(IllegalAccessException.class, () ->
                userService.saveUser(userDTO));
        Assertions.assertEquals("User too young!", exc.getMessage());
    }

    @Test
    public void deveTestarSeUsuarioNaoSeraCadastradoComUsernameDuplicado() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(19);
        userDTO.setUsername("exemplo");

        //Mock
        when(userRepository.findByUsername("exemplo")).thenReturn(new UserEntity());
        UserAlreadyExistsException exc = Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.saveUser(userDTO));
        Assertions.assertEquals("User already exists!", exc.getMessage());
    }

    @Test
    public void deveTestarSeNaoExistirUsuarioDeveSerCadastrado() throws UserAlreadyExistsException, IllegalAccessException {
        UserDTO userDTO = new UserDTO();
        userDTO.setAge(19);
        userDTO.setUsername("exemplo");

        when(userRepository.findByUsername("exemplo")).thenReturn(null);
        userService.saveUser(userDTO);
        verify(userRepository, times(1)).saveUser(ArgumentMatchers.any());
    }

    @Test
    public void deveTestarSeLancaExcecaoNoFindById() {
        //Mock
        when(userRepository.findById(anyString())).thenReturn(null);
        UserDontExistException exc = Assertions.assertThrows(UserDontExistException.class,
                () -> userService.findById("exemplo"));
        Assertions.assertEquals("User dont exist!", exc.getMessage());
    }
}
