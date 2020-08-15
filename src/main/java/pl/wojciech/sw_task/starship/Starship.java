package pl.wojciech.sw_task.starship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wojciech.sw_task.machine.Machine;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship extends Machine {

    private String hyperdrive_rating;
    private String MGLT;
    private String starship_class;
}
