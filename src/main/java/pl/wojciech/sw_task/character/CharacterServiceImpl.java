package pl.wojciech.sw_task.character;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    private final static String SW_API = "https://swapi.dev/api";

    @Override
    public ResponseEntity<?> getCharactersByPageNumber(Long pageNumber) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<CharactersPage> forEntity = restTemplate.getForEntity(SW_API + "/people/?page=" + pageNumber.toString(),
                    CharactersPage.class);

            log.info("Records count: " + forEntity.getBody().getCount());

            return ResponseEntity.ok(forEntity.getBody());

        } catch (RestClientResponseException e) {

            log.error(e.getMessage());

            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

    @Override
    public ResponseEntity<?> getCharacterById(Long id) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Character> forEntity = restTemplate.getForEntity(SW_API + "/people/" + id.toString() + "/",
                    Character.class);

            log.info("Character name: " + forEntity.getBody().getName());

            return ResponseEntity.ok(forEntity.getBody());

        } catch (RestClientResponseException e) {

            log.error(e.getMessage());

            return ResponseEntity
                    .status(e.getRawStatusCode())
                    .body(e.getResponseBodyAsString());
        }
    }

}
