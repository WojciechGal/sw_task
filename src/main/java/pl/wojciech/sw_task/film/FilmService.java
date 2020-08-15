package pl.wojciech.sw_task.film;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public List<Film> getFilmByURIs(List<String> filmURIs) {

        return filmURIs.stream().map(uri -> new RestTemplate().getForEntity(uri, Film.class).getBody()).collect(Collectors.toList());
    }
}
