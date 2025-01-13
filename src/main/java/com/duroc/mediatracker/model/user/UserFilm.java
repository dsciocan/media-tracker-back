package com.duroc.mediatracker.model.user;

import com.duroc.mediatracker.model.info.Film;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name="user_films")
public class UserFilm {

    @EmbeddedId
    private UserFilmId userFilmId;

    private int rating;

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate watchedDate;
}
