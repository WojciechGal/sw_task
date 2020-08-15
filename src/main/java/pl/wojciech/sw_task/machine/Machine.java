package pl.wojciech.sw_task.machine;

import lombok.Data;

@Data
public class Machine {

    private String name;
    private String model;
    private String manufacturer;
    private String cost_in_credits;
    private String length;
    private String max_atmosphering_speed;
    private String crew;
    private String passengers;
    private String cargo_capacity;
    private String consumables;
    private String created;
    private String edited;
}
