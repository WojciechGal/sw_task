package pl.wojciech.sw_task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientResponseException;
import pl.wojciech.sw_task.character.*;
import pl.wojciech.sw_task.character.Character;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CharacterController.class)
public class CharacterControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CharacterService characterService;

    private static final class GenericNotFoundResponse {

        private String detail = "Not found";

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    @Test
    public void givenCharacter_whenGetCharacter_thenCharacterCorrect() throws Exception {

        Character testCharacter = new Character();

        testCharacter.setName("Darth Vader");

        given(characterService.getCharacterById(1L)).willReturn(testCharacter);

        mvc.perform(get("/characters/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(testCharacter.getName())));
    }

    @Test
    public void givenValidatedResponse_whenGetCharacterWrong_thenValidatedResponseReceived() throws Exception {

        given(characterService.getCharacterById(0L)).willThrow(new RestClientResponseException(null,
                404,
                null,
                null,
                new ObjectMapper().writeValueAsBytes(new GenericNotFoundResponse()),
                null));

        mvc.perform(get("/characters/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.detail", is(new GenericNotFoundResponse().getDetail())));
    }

    @Test
    public void givenCharactersPage_whenGetCharactersPage_thenCharactersPageCorrect() throws Exception {

        Character testCharacter = new Character();

        testCharacter.setName("Darth Vader");

        CharactersPage testCharactersPage = new CharactersPage();

        testCharactersPage.setResults(Collections.singletonList(testCharacter));

        given(characterService.getCharactersByPageNumber(1L)).willReturn(testCharactersPage);

        mvc.perform(get("/characters?page=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results[0].name", is(testCharacter.getName())));
    }

    @Test
    public void givenValidatedResponse_whenGetCharactersPageWrong_thenValidatedResponseReceived() throws Exception {

        given(characterService.getCharactersByPageNumber(0L)).willThrow(new RestClientResponseException(null,
                404,
                null,
                null,
                new ObjectMapper().writeValueAsBytes(new GenericNotFoundResponse()),
                null));

        mvc.perform(get("/characters?page=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.detail", is(new GenericNotFoundResponse().getDetail())));
    }

}
