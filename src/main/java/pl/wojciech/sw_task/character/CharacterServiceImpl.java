package pl.wojciech.sw_task.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.FilmService;
import pl.wojciech.sw_task.planet.PlanetService;
import pl.wojciech.sw_task.species.SpeciesService;
import pl.wojciech.sw_task.starship.StarshipService;
import pl.wojciech.sw_task.vehicle.VehicleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private PlanetService planetService;
    private FilmService filmService;
    private SpeciesService speciesService;
    private StarshipService starshipService;
    private VehicleService vehicleService;

    private final static String SW_API = "https://swapi.dev/api";

    @Override
    public CharactersPage getCharactersByPageNumber(Long pageNumber) {

        Map<String, Object> characterPageMap = new RestTemplate().getForObject(SW_API + "/people/?page=" + pageNumber.toString(), Map.class);

        log.info("Records count: " + characterPageMap.get("count"));

        return new ObjectMapper().convertValue(characterPageMap.entrySet().stream().collect(HashMap::new, (map, entry) -> {
            if ("results".equals(entry.getKey())) {
                map.put(entry.getKey(), (((List<Map<String, Object>>) entry.getValue()).stream().map(character -> new ObjectMapper().convertValue(mapCharacterAndFillAdditionalData(character), Character.class)).collect(Collectors.toList())));
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }, HashMap::putAll), CharactersPage.class);
    }

    @Override
    public Character getCharacterById(Long id) {

        Map<String, Object> characterMap = new RestTemplate().getForObject(SW_API + "/people/" + id.toString() + "/", Map.class);

        log.info("Character name: " + characterMap.get("name"));

        return new ObjectMapper().convertValue(mapCharacterAndFillAdditionalData(characterMap), Character.class);
    }

    private Map<String, ?> mapCharacterAndFillAdditionalData(Map<String, Object> characterMap) {
        return characterMap.entrySet().stream().collect(HashMap::new, (map, entry) -> {
            switch (entry.getKey()) {
                case "homeworld":
                    map.put(entry.getKey(), planetService.getPlanetByURI(((String) entry.getValue()).replaceFirst("http", "https")));
                    break;
                case "films":
                    map.put(entry.getKey(), filmService.getFilmByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                    break;
                case "species":
                    map.put(entry.getKey(), speciesService.getSpeciesByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                    break;
                case "vehicles":
                    map.put(entry.getKey(), vehicleService.getVehicleByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                    break;
                case "starships":
                    map.put(entry.getKey(), starshipService.getStarshipByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                    break;
                default:
                    map.put(entry.getKey(), entry.getValue());
                    break;
            }
        }, HashMap::putAll);
    }
}
