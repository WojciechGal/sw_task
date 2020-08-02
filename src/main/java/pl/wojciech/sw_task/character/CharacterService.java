package pl.wojciech.sw_task.character;

public interface CharacterService {

    CharactersPage getCharactersByPageNumber(Long pageNumber);

    Character getCharacterById(Long id);

}
