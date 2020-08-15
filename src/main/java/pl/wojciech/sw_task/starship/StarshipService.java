package pl.wojciech.sw_task.starship;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StarshipService {

    public List<Starship> getStarshipByURIs(List<String> starshipURIs) {

        return starshipURIs.stream().map(uri -> new RestTemplate().getForEntity(uri, Starship.class).getBody()).collect(Collectors.toList());
    }
}
