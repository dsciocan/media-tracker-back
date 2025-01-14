package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.user.UserFilm;
import com.duroc.mediatracker.model.user.UserFilmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilmRepository extends JpaRepository<UserFilm, UserFilmId> {
}
