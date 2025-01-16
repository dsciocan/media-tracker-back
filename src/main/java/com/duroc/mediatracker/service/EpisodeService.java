package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.episode_search.EpisodeSearchResult;
import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;

import java.io.IOException;
import java.util.List;

public interface EpisodeService {
    List<EpisodeSearchResult> getAllEpisodes(Long apiShowId, int numOfSeasons) throws IOException, InterruptedException;

//    List<Episode> getAllEpisodes2(Long apiShowId, int numOfSeasons) throws IOException, InterruptedException;

    void saveEpisodes(Long apiShowId, int numOfSeasons, Show show) throws IOException, InterruptedException;

    List<Episode> getSavedEpisodesByShowId(Long showId);

    List<Episode> getSavedEpisodesBySeason(Long showId, int seasonNumber) throws IOException, InterruptedException;
}
