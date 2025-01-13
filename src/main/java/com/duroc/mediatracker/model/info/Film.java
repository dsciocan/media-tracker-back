package com.duroc.mediatracker.model.info;

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
@Table(name="films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String synopsis;

    private int releaseYear;

    private int duration;

    @Enumerated(EnumType.STRING)
    private List<FilmGenres> genres;

    private String director;

    private String country;

    private String language;

//- Age Rating
}
