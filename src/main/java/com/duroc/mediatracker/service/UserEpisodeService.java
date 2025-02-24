package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserEpisode;

import java.util.List;

public interface UserEpisodeService {
    List<UserEpisode> getUserEpisodeListByShowId(Long showId);

    List<UserEpisode> saveAllShowEpisodesAsUserEpisodes(Long showId);

    UserEpisode getUserEpisodeByEpisodeId(Long episodeId);

    UserEpisode changeUserEpisodeDetails(Long episodeId, UserEpisode newUserEpisode);

    int getAllRuntimeWatched();

    UserEpisode getMostRecentEpisode(Long showId);
}
