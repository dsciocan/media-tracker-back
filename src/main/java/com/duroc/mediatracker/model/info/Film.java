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
    Long id;

    String title;

    String synopsis;

    int releaseYear;

    int duration;

    @Enumerated(EnumType.STRING)
    List<FilmGenres> genres;

    String director;

    String country;

    String language;

//- Age Rating
}
