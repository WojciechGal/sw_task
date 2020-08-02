package pl.wojciech.sw_task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wojciech.sw_task.character.*;
import pl.wojciech.sw_task.character.Character;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class CharacterControllerIntegrationTest {

    @TestConfiguration
    static class CharacterControllerTestContextConfiguration {

        @Bean
        public CharacterController characterController() {
            return new CharacterController(characterService());
        }

        @Bean
        public CharacterService characterService() {
            return new CharacterServiceImpl();
        }

    }

    @Autowired
    private CharacterController characterController;

    @Test
    public void ifControllerGetCharacterFromServiceCharacterNotNull() {

        Character testCharacter = characterController.getCharacter(1L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void ifControllerCharacterHasBeenValidated() {

        Character testCharacter = characterController.getCharacter(0L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void ifControllerGetCharactersPageFromServiceCharactersPageNotNull() {

        CharactersPage testCharactersPage = characterController.getCharacters(1L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

    @Test
    public void ifControllerCharactersPageHasBeenValidated() {

        CharactersPage testCharactersPage = characterController.getCharacters(0L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

}
