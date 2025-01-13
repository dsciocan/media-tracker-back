package com.duroc.mediatracker.model.user;

import com.duroc.mediatracker.model.info.Film;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class UserFilmId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
