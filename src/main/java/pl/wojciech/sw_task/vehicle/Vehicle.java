package pl.wojciech.sw_task.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wojciech.sw_task.machine.Machine;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle extends Machine {

    private String vehicle_class;
}
