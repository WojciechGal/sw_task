package pl.wojciech.sw_task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wojciech.sw_task.character.CharacterService;
import pl.wojciech.sw_task.character.CharacterServiceImpl;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
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
    public void givenCharacterId_whenServiceImplHasConnection_thenCharacterIsNotNull() {

        ResponseEntity<?> testCharacter = characterService.getCharacterById(1L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void givenCharacterId_whenServiceImplHasConnection_thenWrongIdHasBeenValidated() {

        ResponseEntity<?> testCharacter = characterService.getCharacterById(0L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void givenPageNumber_whenServiceImplHasConnection_thenCharactersPageIsNotNull() {

        ResponseEntity<?> testCharactersPage = characterService.getCharactersByPageNumber(1L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

    @Test
    public void givenPageNumber_whenServiceImplHasConnection_thanWrongCharactersPageHasBeenValidated() {

        ResponseEntity<?> testCharactersPage = characterService.getCharactersByPageNumber(0L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

}
