package pl.wojciech.sw_task.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharactersPage {

    private Long count;
    private String next;
    private String previous;
    private List<Character> results;
}
