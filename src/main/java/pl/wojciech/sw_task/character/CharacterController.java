package pl.wojciech.sw_task.character;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    @GetMapping(produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    response = CharactersPage.class)})
    public ResponseEntity<?> getCharacters(@RequestParam(defaultValue = "1") Long page) {

        try {
            return ResponseEntity.ok(characterService.getCharactersByPageNumber(page));

        } catch (RestClientResponseException e) {

            log.error(e.getMessage());

            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    response = Character.class)})
    public ResponseEntity<?> getCharacter(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(characterService.getCharacterById(id));

        } catch (RestClientResponseException e) {

            log.error(e.getMessage());

            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }
}
