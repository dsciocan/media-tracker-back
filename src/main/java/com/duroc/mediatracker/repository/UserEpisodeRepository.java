package com.duroc.mediatracker.repository;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserEpisodeId;
import com.duroc.mediatracker.model.user.UserShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEpisodeRepository extends JpaRepository<UserEpisode, UserEpisodeId> {
    List<UserEpisode> findByUserEpisodeIdAppUser(AppUser user);

}
