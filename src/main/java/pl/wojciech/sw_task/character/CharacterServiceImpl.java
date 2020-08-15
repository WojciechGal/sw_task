package pl.wojciech.sw_task.character;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.FilmService;
import pl.wojciech.sw_task.planet.PlanetService;
import pl.wojciech.sw_task.species.SpeciesService;
import pl.wojciech.sw_task.starship.StarshipService;
import pl.wojciech.sw_task.vehicle.VehicleService;

import java.util.stream.Collectors;

@Service
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    public CharacterServiceImpl(PlanetService planetService, FilmService filmService, SpeciesService speciesService, StarshipService starshipService, VehicleService vehicleService) {
        this.planetService = planetService;
        this.filmService = filmService;
        this.speciesService = speciesService;
        this.starshipService = starshipService;
        this.vehicleService = vehicleService;
    }

    private PlanetService planetService;
    private FilmService filmService;
    private SpeciesService speciesService;
    private StarshipService starshipService;
    private VehicleService vehicleService;

    private final static String SW_API = "https://swapi.dev/api";

    @Override
    public CharactersPage getCharactersByPageNumber(Long pageNumber) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CharactersPageDTO> forEntity = restTemplate.getForEntity(SW_API + "/people/?page=" + pageNumber.toString(),
                CharactersPageDTO.class);

        log.info("Records count: " + forEntity.getBody().getCount());

        return mapCharPageDTOtoCharacterPage(forEntity.getBody());
    }

    @Override
    public Character getCharacterById(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CharacterDTO> forEntity = restTemplate.getForEntity(SW_API + "/people/" + id.toString() + "/",
                CharacterDTO.class);

        log.info("Character name: " + forEntity.getBody().getName());

        return mapCharDTOtoCharacter(forEntity.getBody());
    }

    private Character mapCharDTOtoCharacter(CharacterDTO characterDTO) {
        return new Character(characterDTO.getName(),
                characterDTO.getHeight(),
                characterDTO.getMass(),
                characterDTO.getHair_color(),
                characterDTO.getSkin_color(),
                characterDTO.getEye_color(),
                characterDTO.getBirth_year(),
                characterDTO.getGender(),
                planetService.getPlanetByURI(characterDTO.getHomeworld().replaceFirst("http", "https")),
                filmService.getFilmByURIs(characterDTO.getFilms().stream().map(film -> film.replaceFirst("http", "https")).collect(Collectors.toList())),
                speciesService.getSpeciesByURIs(characterDTO.getSpecies().stream().map(species -> species.replaceFirst("http", "https")).collect(Collectors.toList())),
                vehicleService.getVehicleByURIs(characterDTO.getVehicles().stream().map(vehicle -> vehicle.replaceFirst("http", "https")).collect(Collectors.toList())),
                starshipService.getStarshipByURIs(characterDTO.getStarships().stream().map(starship -> starship.replaceFirst("http", "https")).collect(Collectors.toList())),
                characterDTO.getCreated(),
                characterDTO.getEdited());
    }

    private CharactersPage mapCharPageDTOtoCharacterPage(CharactersPageDTO charactersPageDTO) {
        return new CharactersPage(charactersPageDTO.getCount(),
                charactersPageDTO.getNext(),
                charactersPageDTO.getPrevious(),
                charactersPageDTO.getResults().stream().map(this::mapCharDTOtoCharacter).collect(Collectors.toList()));
    }
}
