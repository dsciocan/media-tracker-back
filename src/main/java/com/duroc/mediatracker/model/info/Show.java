package com.duroc.mediatracker.model.info;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String synopsis;

    int releaseYear;

    int finishedYear;

    boolean isComplete;

    @Enumerated(EnumType.STRING)
    List<TvGenres> genres;

    int numberOfSeasons;

    int numberOfEpisodes;

    String country;

    String language;

    @OneToMany(mappedBy = "episodes", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Episode> episodes;

}
