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
@Table(name="user_shows")
public class UserShow {


    @EmbeddedId
    private UserShowId userShowId;

    private int rating;

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate dateStarted;

    private LocalDate dateCompleted;

}
