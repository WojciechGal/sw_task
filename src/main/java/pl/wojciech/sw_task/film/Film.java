package pl.wojciech.sw_task.film;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {

    private String title;
    private Long episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    private String created;
    private String edited;
}
