package pl.wojciech.sw_task.vehicle;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.wojciech.sw_task.film.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    public List<Vehicle> getVehicleByURIs(List<String> vehicleURIs) {

        return vehicleURIs.stream().map(uri -> new RestTemplate().getForEntity(uri, Vehicle.class).getBody()).collect(Collectors.toList());
    }
}
