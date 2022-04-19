package com.dbccompany.trabalhofinalmod5.tests;

import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipesServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(recipeService, "objectMapper", objectMapper);
    }

    //===> MOCK
    @Test
    public void deveTestarSeLancaExcecaoParaUsuarioInativo() {
        RecipeDTO recipeDTO = RecipeDTO.builder()
                .author("exemplo")
                .calories(1250.0)
                .price(32.12).build();
        UserEntity user = new UserEntity();
        user.setIsactive(false);

        //Mock
        when(userRepository.findByUsername(recipeDTO.getAuthor())).thenReturn(user);
        verifyNoInteractions(recipeRepository);

        //Validação
        IllegalAccessException exc = assertThrows(IllegalAccessException.class,
                () -> recipeService.saveRecipe(recipeDTO));
        assertEquals("User not active!", exc.getMessage());
    }

    @Test
    public void deveTestarSeLancaExcecaoParaLimiteDeCalorias() {
        RecipeDTO recipeDTO = RecipeDTO.builder()
                .price(32.12)
                .calories(1600.0).build();

        CaloriesLimitExceededException exc = assertThrows(CaloriesLimitExceededException.class,
                () -> recipeService.saveRecipe(recipeDTO));
        assertEquals("Food too much fat!", exc.getMessage());
    }

    @Test
    public void deveTestarSeLancaExcecaoParaPrecoLimite() {
        //Construção de objetos
        RecipeDTO recipeDTO = RecipeDTO.builder()
                .price(355.0).build();

        PriceExpensiveException exc = assertThrows(PriceExpensiveException.class,
                () -> recipeService.saveRecipe(recipeDTO));
        assertEquals("Price too much expensive!", exc.getMessage());
    }

    @Test
    public void deveTestarSeChamaMetodoRepositorySave() throws CaloriesLimitExceededException, PriceExpensiveException, IllegalAccessException, UserDontExistException {
        RecipeDTO recipeDTO = RecipeDTO.builder()
                .author("exemplo")
                .calories(1250.0)
                .price(32.12).build();

        UserEntity user = new UserEntity();
        user.setUsername("exemplo");
        user.setIsactive(true);
        when(userRepository.findByUsername("exemplo")).thenReturn(user);

        recipeService.saveRecipe(recipeDTO);
        verify(recipeRepository, times(1))
                .saveRecipe(ArgumentMatchers.any());
    }

    @Test
    public void deveTestarSeRecipeNotFoudELancada ()  {
        when(recipeRepository.findById(anyString())).thenReturn(null);
        RecipeNotFoundException exc = assertThrows(RecipeNotFoundException.class,
                () -> recipeService.findById(anyString()));
        assertEquals("Recipe not found!", exc.getMessage());
    }

}
