package pl.wojciech.sw_task.character;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final static String SW_API = "https://swapi.dev/api";

    @Override
    public CharactersPage getCharactersByPageNumber(Long pageNumber) {

        RestTemplate restTemplate = new RestTemplate();

        //todo walidacja przekroczonego parametru
        ResponseEntity<CharactersPage> forEntity = restTemplate.getForEntity(SW_API + "/people/?page=" + pageNumber.toString(), CharactersPage.class);

        return forEntity.getBody();

    }

    @Override
    public Character getCharacterById(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        //todo walidacja przekroczonego parametru
        ResponseEntity<Character> forEntity = restTemplate.getForEntity(SW_API + "/people/" + id.toString() + "/", Character.class);

        return forEntity.getBody();

    }

}
