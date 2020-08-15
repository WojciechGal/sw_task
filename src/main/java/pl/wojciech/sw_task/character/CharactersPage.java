package pl.wojciech.sw_task.character;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CharactersPage {

    private Long count;
    private String next;
    private String previous;
    private List<Character> results;
}
