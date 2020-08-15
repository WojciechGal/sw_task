package pl.wojciech.sw_task.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharactersPageDTO {

    private Long count;
    private String next;
    private String previous;
    private List<CharacterDTO> results;
}
