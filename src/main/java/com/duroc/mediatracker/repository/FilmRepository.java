package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.info.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
