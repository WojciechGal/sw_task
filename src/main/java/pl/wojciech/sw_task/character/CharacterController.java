package pl.wojciech.sw_task.character;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    @GetMapping
    public CharactersPage getCharacters(@RequestParam(defaultValue = "1L") Long page) {
        return characterService.getCharactersByPageNumber(page);
    }

    @GetMapping("/{id}")
    public Character getCharacter(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

}
