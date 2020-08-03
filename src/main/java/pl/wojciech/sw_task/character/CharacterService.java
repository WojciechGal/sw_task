package pl.wojciech.sw_task.character;

import org.springframework.http.ResponseEntity;

public interface CharacterService {

    ResponseEntity<?> getCharactersByPageNumber(Long pageNumber);

    ResponseEntity<?> getCharacterById(Long id);
}
