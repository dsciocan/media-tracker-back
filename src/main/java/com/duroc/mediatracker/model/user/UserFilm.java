package com.duroc.mediatracker.model.user;

import com.duroc.mediatracker.model.info.Film;
import jakarta.persistence.*;

import java.time.LocalDate;

public class UserFilm {

    @EmbeddedId
    private UserFilmId userFilmId;

    private int rating;

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate watchedDate;
}
