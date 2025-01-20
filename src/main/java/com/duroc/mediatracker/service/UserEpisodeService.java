package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserEpisode;

import java.util.List;

public interface UserEpisodeService {
    List<UserEpisode> getUserEpisodeListByShowId(Long userId, Long showId);

    List<UserEpisode> saveAllShowEpisodesAsUserEpisodes(Long userId, Long showId);

    UserEpisode getUserEpisodeByEpisodeId(Long userId, Long episodeId);

    UserEpisode changeUserEpisodeDetails(Long userId, Long episodeId, UserEpisode newUserEpisode);
}
