package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserEpisodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEpisodeRepository extends JpaRepository<UserEpisode, UserEpisodeId> {
}
