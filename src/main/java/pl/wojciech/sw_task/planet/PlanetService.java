package pl.wojciech.sw_task.planet;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlanetService {

    public Planet getPlanetByURI(String planetURI) {

        return new RestTemplate().getForEntity(planetURI, Planet.class).getBody();
    }
}
