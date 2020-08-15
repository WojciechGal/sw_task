package pl.wojciech.sw_task.species;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeciesService {

    public List<Species> getSpeciesByURIs(List<String> speciesURIs) {

        return speciesURIs.stream().map(uri -> new RestTemplate().getForEntity(uri, Species.class).getBody()).collect(Collectors.toList());
    }
}
