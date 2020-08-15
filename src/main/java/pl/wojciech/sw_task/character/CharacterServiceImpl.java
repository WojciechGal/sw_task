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

        RestTemplate restTemplate = new RestTemplate();

//        ResponseEntity<CharactersPageDTO> forEntity = restTemplate.getForEntity(SW_API + "/people/?page=" + pageNumber.toString(),
//                CharactersPageDTO.class);
//
//        log.info("Records count: " + forEntity.getBody().getCount());
//
//        return mapCharPageDTOtoCharacterPage(forEntity.getBody());
        return null;
    }

    @Override
    public Character getCharacterById(Long id) {

        Map<String, Object> characterMap = new RestTemplate().getForObject(SW_API + "/people/" + id.toString() + "/", Map.class);
//        for (Map.Entry entry: characterMap.entrySet()){
//        if ("homeworld".equals(entry.getKey())) {
//            entry.setValue(planetService.getPlanetByURI((String) entry.getValue()));
//        } else if ("films".equals(entry.getKey())) {
//            entry.setValue(filmService.getFilmByURIs((List<String>) entry.getValue()));
//        } else if ("species".equals(entry.getKey())) {
//            entry.setValue(speciesService.getSpeciesByURIs((List<String>) entry.getValue()));
//        } else if ("vehicles".equals(entry.getKey())) {
//            entry.setValue(vehicleService.getVehicleByURIs((List<String>) entry.getValue()));
//        } else if ("starships".equals(entry.getKey())) {
//            entry.setValue(starshipService.getStarshipByURIs((List<String>) entry.getValue()));
//        }
//        }

        // characterMap.put("homeworld", planetService.getPlanetByURI(characterMap.));

        //log.info("Character name: " + forEntity.getBody().getName());

//        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
//        final CharacterDTO pojo = mapper.convertValue(characterMap, CharacterDTO.class);
        return new ObjectMapper().convertValue(characterMap.entrySet().stream().map(entry -> {
            if ("homeworld".equals(entry.getKey())) {
                entry.setValue(planetService.getPlanetByURI(((String) entry.getValue()).replaceFirst("http", "https")));
                return entry;
            } else if ("films".equals(entry.getKey())) {
                entry.setValue(filmService.getFilmByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                return entry;
            } else if ("species".equals(entry.getKey())) {
                entry.setValue(speciesService.getSpeciesByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                return entry;
            } else if ("vehicles".equals(entry.getKey())) {
                entry.setValue(vehicleService.getVehicleByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                return entry;
            } else if ("starships".equals(entry.getKey())) {
                entry.setValue(starshipService.getStarshipByURIs(((List<String>) entry.getValue()).stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())));
                return entry;
            } else {
                return entry;
            }
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), Character.class);
    }
}
