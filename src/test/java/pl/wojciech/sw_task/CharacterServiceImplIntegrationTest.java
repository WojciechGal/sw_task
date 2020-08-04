package pl.wojciech.sw_task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientResponseException;
import pl.wojciech.sw_task.character.Character;
import pl.wojciech.sw_task.character.CharacterService;
import pl.wojciech.sw_task.character.CharacterServiceImpl;
import pl.wojciech.sw_task.character.CharactersPage;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class CharacterServiceImplIntegrationTest {

    /*
    serwis powinien mockować warstwę repozytorium i pracować na fałszywych danych,
    jednak ze względu na brak w/w warstwy w tej klasie testowej
    sprawdzane jest realne połączenie do zewnętrznego API
     */

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

        Character testCharacter = characterService.getCharacterById(1L);

        assertThat(testCharacter, is(notNullValue()));
    }

    @Test
    public void givenCharacterId_whenServiceImplHasConnection_thenWrongIdThrowsError() {

        Exception exception = assertThrows(RestClientResponseException.class, () -> {
            characterService.getCharacterById(0L);
        });

        String expectedMessage = "404 NOT FOUND: [{\"detail\":\"Not found\"}]";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void givenPageNumber_whenServiceImplHasConnection_thenCharactersPageIsNotNull() {

        CharactersPage testCharactersPage = characterService.getCharactersByPageNumber(1L);

        assertThat(testCharactersPage, is(notNullValue()));
    }

    @Test
    public void givenPageNumber_whenServiceImplHasConnection_thanWrongCharactersPageThrowsError() {

        Exception exception = assertThrows(RestClientResponseException.class, () -> {
            characterService.getCharactersByPageNumber(0L);
        });

        String expectedMessage = "404 NOT FOUND: [{\"detail\":\"Not found\"}]";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

}
