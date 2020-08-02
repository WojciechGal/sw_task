package pl.wojciech.sw_task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wojciech.sw_task.character.Character;
import pl.wojciech.sw_task.character.CharacterService;
import pl.wojciech.sw_task.character.CharacterServiceImpl;
import pl.wojciech.sw_task.character.CharactersPage;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class CharacterServiceImplIntegrationTest {

    @TestConfiguration
    static class CharacterServiceImplTestContextConfiguration {

        @Bean
        public CharacterService characterService() {
            return new CharacterServiceImpl();
        }

    }

    @Autowired
    private CharacterService characterService;

    @Test
    public void ifServiceImplHasConnectionCharacterNotNull() {

        Character testCharacter = characterService.getCharacterById(1L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void ifServiceImplCharacterHasBeenValidated() {

        Character testCharacter = characterService.getCharacterById(0L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void ifServiceImplHasConnectionCharactersPageNotNull() {

        CharactersPage testCharactersPage = characterService.getCharactersByPageNumber(1L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

    @Test
    public void ifServiceImplCharactersPageHasBeenValidated() {

        CharactersPage testCharactersPage = characterService.getCharactersByPageNumber(0L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

}
