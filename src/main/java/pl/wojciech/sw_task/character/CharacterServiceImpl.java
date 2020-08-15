package pl.wojciech.sw_task.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.FilmService;
import pl.wojciech.sw_task.planet.PlanetService;
import pl.wojciech.sw_task.species.SpeciesService;
import pl.wojciech.sw_task.starship.StarshipService;
import pl.wojciech.sw_task.vehicle.VehicleService;

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

        return new ObjectMapper().convertValue(characterPageMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            if ("results".equals(entry.getKey())) {
                return (((List<Map<String, Object>>) entry.getValue()).stream().map(character -> new ObjectMapper().convertValue(character.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, characterEntry -> {
                    System.out.println(characterEntry.getKey() + " - " + characterEntry.getValue());
                    switch(characterEntry.getKey()) {
                        case "homeworld":
                            return planetService.getPlanetByURI(((String) characterEntry.getValue()).replaceFirst("http", "https"));
                        case "films":
                            return filmService.getFilmByURIs(((List<String>) characterEntry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                        case "species":
                            return speciesService.getSpeciesByURIs(((List<String>) characterEntry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                        case "vehicles":
                            return vehicleService.getVehicleByURIs(((List<String>) characterEntry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                        case "starships":
                            return starshipService.getStarshipByURIs(((List<String>) characterEntry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                        default:
                            return characterEntry.getValue();
                    }
                })), Character.class)).collect(Collectors.toList()));
            }
            return entry.getValue();
        })), CharactersPage.class);
    }

    @Override
    public Character getCharacterById(Long id) {

        Map<String, Object> characterMap = new RestTemplate().getForObject(SW_API + "/people/" + id.toString() + "/", Map.class);

        log.info("Character name: " + characterMap.get("name"));

        return new ObjectMapper().convertValue(characterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            switch(entry.getKey()) {
                case "homeworld":
                    return planetService.getPlanetByURI(((String) entry.getValue()).replaceFirst("http", "https"));
                case "films":
                    return filmService.getFilmByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                case "species":
                    return speciesService.getSpeciesByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                case "vehicles":
                    return vehicleService.getVehicleByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                case "starships":
                    return starshipService.getStarshipByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList()));
                default:
                    return entry.getValue();
            }
        })), Character.class);
    }
}
