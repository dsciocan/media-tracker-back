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

    @Lob
    private String synopsis;

    private int releaseYear;

    @Column(name="duration_minutes")
    private int duration;

    private List<String> genres;

    private List<String> productionCompanies;

    private String country;

    private String language;

    private String poster_url;

}
