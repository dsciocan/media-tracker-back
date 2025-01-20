package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.dao.EpisodeDAO;
import com.duroc.mediatracker.model.episode_search.EpisodeSearchResult;
import com.duroc.mediatracker.model.episode_search.Result;
import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EpisodeServiceImplementation implements EpisodeService {
    @Autowired
    EpisodeRepository episodeRepository;

    @Override
    public List<EpisodeSearchResult> getAllEpisodes(Long apiShowId, int numOfSeasons) throws IOException, InterruptedException {
        List<EpisodeSearchResult> episodeList = new ArrayList<>();
        for (int i = 1; i <= numOfSeasons; i++) {
            episodeList.add(EpisodeDAO.requestEpisodesBySeason(apiShowId, i));
        }
        return episodeList;
    }

//    @Override
//    public List<Episode> getAllEpisodes2(Long apiShowId, int numOfSeasons) throws IOException, InterruptedException {
//        List<Episode> episodeList = new ArrayList<>();
//        for (int i = 1; i <= numOfSeasons; i++) {
//            EpisodeSearchResult episodeSearchResult = EpisodeDAO.requestEpisodesBySeason(apiShowId, i);
//            for(Result result : episodeSearchResult.episodes()) {
//                Episode newEpisode = Episode.builder().title(result.name()).episodeNumber(result.episode_number()).seasonNumber(result.season_number()).description(result.overview()).runtime(result.runtime()).build();
//                episodeList.add(newEpisode);
//            }
//        }
//        return episodeList;
//    }



    @Override
    public void saveEpisodes(Long apiShowId, int numOfSeasons, Show show) throws IOException, InterruptedException {
        for (int i = 1; i <= numOfSeasons; i++) {
            EpisodeSearchResult episodeSearchResult = EpisodeDAO.requestEpisodesBySeason(apiShowId, i);
            for(Result result : episodeSearchResult.episodes()) {
                Episode newEpisode = Episode.builder().show(show).title(result.name()).description(result.overview()).seasonNumber(result.season_number()).episodeNumber(result.episode_number()).runtime(result.runtime()).build();
                episodeRepository.save(newEpisode);
            }
        }

    }

    @Override
    public Episode getSavedEpisodeById(Long episodeId) {
        if(episodeRepository.findById(episodeId).isPresent()) {
            return episodeRepository.findById(episodeId).get();
        } else {
            throw new ItemNotFoundException("Could not find episode with specified id");
        }
    }

    @Override
    public List<Episode> getSavedEpisodesByShowId(Long showId) {
        List<Episode> episodeList = new ArrayList<>();
        episodeRepository.findAll().forEach(episode -> {
            if(Objects.equals(episode.getShow().getId(), showId)) {
                episodeList.add(episode);
            }
        });
        return episodeList;
    }

    @Override
    public List<Episode> getSavedEpisodesBySeason(Long showId, int seasonNumber) throws IOException, InterruptedException {
        List<Episode> allEpisodes = getSavedEpisodesByShowId(showId);
        List<Episode> seasonEpisodes = new ArrayList<>();
        for(Episode episode : allEpisodes) {
            if(episode.getSeasonNumber() == seasonNumber) {
                seasonEpisodes.add(episode);
            }
        }
        return seasonEpisodes;
    }
}
