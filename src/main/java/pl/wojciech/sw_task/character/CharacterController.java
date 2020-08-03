package pl.wojciech.sw_task.character;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getCharacters(@RequestParam(defaultValue = "1") Long page) {
        return characterService.getCharactersByPageNumber(page);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getCharacter(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }
}
