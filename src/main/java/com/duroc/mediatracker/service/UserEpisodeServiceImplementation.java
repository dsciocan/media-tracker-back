package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.user.*;
import com.duroc.mediatracker.repository.UserEpisodeRepository;
import com.duroc.mediatracker.repository.UserShowRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEpisodeServiceImplementation implements UserEpisodeService {

    @Autowired
    UserEpisodeRepository userEpisodeRepository;

    @Autowired
    EpisodeService episodeService;

    @Autowired
    UserShowRepository userShowRepository;

    @Autowired
    UserService userService;

    @Autowired
    ShowService showService;

    @Override
    public List<UserEpisode> getUserEpisodeListByShowId(Long showId) {

        return userEpisodeRepository.findByShowId(showId);

    }


    @Override
    public List<UserEpisode> saveAllShowEpisodesAsUserEpisodes(Long showId) {
        AppUser user = userService.getUser();
        List<Episode> showEpisodes = episodeService.getSavedEpisodesByShowId(showId);
        UserShowId userShowId = new UserShowId(user, showService.getSavedShow(showId));
        UserShow show = userShowRepository.findById(userShowId).orElseThrow();
        List<UserEpisode> userEpisodeList = new ArrayList<>();
        showEpisodes.forEach((ep) ->
        {
            UserEpisodeId userEpisodeId = new UserEpisodeId(user, ep);
            UserEpisode userEpisode = new UserEpisode(userEpisodeId, 0, "", false, null, showService.getSavedShow(showId));
            if(show.getStatus().equalsIgnoreCase("WATCHED")) {
                userEpisode.setWatched(true);
            }
            userEpisodeList.add(userEpisode);
            userEpisodeRepository.save(userEpisode);
        });
        return userEpisodeList;
    }

    @Override
    public UserEpisode getUserEpisodeByEpisodeId(Long episodeId) {
        AppUser user = userService.getUser();
        Episode episode = episodeService.getSavedEpisodeById(episodeId);
        UserEpisodeId userEpisodeId = new UserEpisodeId(user, episode);
        if(userEpisodeRepository.findById(userEpisodeId).isPresent()) {
            return userEpisodeRepository.findById(userEpisodeId).get();
        } else {
            throw new ItemNotFoundException("Could not find user episode with specified id");
        }}


    @Override
    public UserEpisode changeUserEpisodeDetails(Long episodeId, UserEpisode newUserEpisode) {
        AppUser user = userService.getUser();
        Episode episode = episodeService.getSavedEpisodeById(episodeId);
        UserEpisodeId userEpisodeId = new UserEpisodeId(user, episode);
        UserEpisode userEpisode = userEpisodeRepository.findById(userEpisodeId).orElseThrow();
        if(newUserEpisode.getUserEpisodeId() == null) {
            newUserEpisode.setUserEpisodeId(userEpisode.getUserEpisodeId());
        }
            userEpisode.setNotes(newUserEpisode.getNotes());
            if(newUserEpisode.isWatched() && !userEpisode.isWatched()) {
                userEpisode.setWatchedDate(LocalDate.now());
            }
            userEpisode.setWatched(newUserEpisode.isWatched());
            userEpisode.setRating(newUserEpisode.getRating());
            return userEpisodeRepository.save(userEpisode);
    }


    @Override
    public UserEpisode getMostRecentEpisode(Long showId) {
        List<UserEpisode> userEpisodeList = getUserEpisodeListByShowId(showId);
        if(userEpisodeList.getLast().isWatched()) {
            return userEpisodeList.getLast();
        } else {
            for (int i = userEpisodeList.size() - 1; i >= 0; i--) {
                if (userEpisodeList.get(i).isWatched()) {
                    return userEpisodeList.get(i + 1);
                }
            }
        }
        return userEpisodeList.getFirst();
    }

    @Override
    public int getAllRuntimeWatched() {
        AppUser user = userService.getUser();
        List<UserEpisode> allUserEpisodes = userEpisodeRepository.findByUserEpisodeIdAppUser(user);
        int totalWatchedRuntime = 0;
        for(UserEpisode userEpisode : allUserEpisodes) {
            if(userEpisode.isWatched()) {
                totalWatchedRuntime += userEpisode.getUserEpisodeId().getEpisode().getRuntime();
            }
        }
        return totalWatchedRuntime;
    }
}

