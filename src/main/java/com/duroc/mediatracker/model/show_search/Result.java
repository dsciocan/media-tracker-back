package com.duroc.mediatracker.model.show_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private long id;
    private String name;
    private String overview;
    private String poster_path;
    private String first_air_date;

}
