package pl.wojciech.sw_task.character;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.wojciech.sw_task.film.Film;
import pl.wojciech.sw_task.planet.Planet;
import pl.wojciech.sw_task.species.Species;
import pl.wojciech.sw_task.starship.Starship;
import pl.wojciech.sw_task.vehicle.Vehicle;

import java.util.List;

@Data
@AllArgsConstructor
public class Character {

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private Planet homeworld;
    private List<Film> films;
    private List<Species> species;
    private List<Vehicle> vehicles;
    private List<Starship> starships;
    private String created;
    private String edited;
}
