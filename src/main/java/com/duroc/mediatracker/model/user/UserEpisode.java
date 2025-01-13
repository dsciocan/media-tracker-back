package com.duroc.mediatracker.model.user;


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
@Table(name="users_episodes")
public class UserEpisode {

    @EmbeddedId
    private UserEpisodeId userEpisodeId;

    private int rating;

    private String notes;

    private boolean isWatched;

    private LocalDate watchedDate;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private UserShow userShow;
}
