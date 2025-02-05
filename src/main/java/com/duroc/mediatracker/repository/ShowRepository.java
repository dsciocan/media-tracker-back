package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.info.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    public List<Show> findShowByTmdbId(Long tmdbId);
}
