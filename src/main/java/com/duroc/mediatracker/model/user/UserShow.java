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
@Table(name="users_shows")
public class UserShow {


    @EmbeddedId
    private UserShowId userShowId;

    private int rating;

    private String notes;

    private String status;

    private LocalDate dateStarted;

    private LocalDate dateCompleted;

}
