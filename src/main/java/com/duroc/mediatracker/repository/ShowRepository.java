package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.info.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
}
