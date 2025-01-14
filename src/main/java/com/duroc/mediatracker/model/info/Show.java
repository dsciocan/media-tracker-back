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
    private Long id;

    private String title;

    private String synopsis;

    private int releaseYear;

    private int finishedYear;

    private boolean isComplete;

    @ElementCollection(targetClass = TvGenres.class)
    @Enumerated(EnumType.STRING)
    private List<TvGenres> genres;

    private int numberOfSeasons;

    private int numberOfEpisodes;

    private String country;

    private String language;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Episode> episodes;

}
