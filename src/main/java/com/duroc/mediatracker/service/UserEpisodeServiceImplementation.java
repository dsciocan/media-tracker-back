package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.info.Episode;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserEpisodeId;
import com.duroc.mediatracker.repository.UserEpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserEpisodeServiceImplementation implements UserEpisodeService {

    @Autowired
    UserEpisodeRepository userEpisodeRepository;

    @Autowired
    EpisodeService episodeService;

    @Autowired
    UserService userService;

    @Override
    public List<UserEpisode> getUserEpisodeListByShowId(Long userId, Long showId) {
        AppUser user = userService.getUserById(userId);
        List<UserEpisode> allUserEpisodes = userEpisodeRepository.findByUserEpisodeIdAppUser(user);
        return allUserEpisodes.stream().filter(ep -> Objects.equals(ep.getUserEpisodeId().getEpisode().getShow().getId(), showId)).toList();
    }

    @Override
    public List<UserEpisode> saveAllShowEpisodesAsUserEpisodes(Long userId, Long showId) {
        AppUser user = userService.getUserById(userId);
        List<Episode> showEpisodes = episodeService.getSavedEpisodesByShowId(showId);
        List<UserEpisode> userEpisodeList = new ArrayList<>();
        showEpisodes.forEach((ep) ->
        {
            UserEpisodeId userEpisodeId = new UserEpisodeId(user, ep);
            userEpisodeList.add(new UserEpisode(userEpisodeId, 0, "", false, null));
        });
        return userEpisodeList;
    }

    @Override
    public UserEpisode getUserEpisodeByEpisodeId(Long userId, Long episodeId) {
        AppUser user = userService.getUserById(userId);
        Episode episode = episodeService.getSavedEpisodeById(episodeId);
        UserEpisodeId userEpisodeId = new UserEpisodeId(user, episode);
        if(userEpisodeRepository.findById(userEpisodeId).isPresent()) {
            return userEpisodeRepository.findById(userEpisodeId).get();
        } else {
            throw new ItemNotFoundException("Could not find user episode with specified id");
        }}


    @Override
    public UserEpisode changeUserEpisodeDetails(Long userId, Long episodeId, UserEpisode newUserEpisode) {
        UserEpisode userEpisode = getUserEpisodeByEpisodeId(userId, episodeId);
        if(newUserEpisode.getUserEpisodeId() == null) {
            newUserEpisode.setUserEpisodeId(userEpisode.getUserEpisodeId());
        }
        if(!Objects.equals(newUserEpisode.getUserEpisodeId(), userEpisode.getUserEpisodeId())) {
            throw new InvalidItemException("User and Episode properties are not changeable");
        } else {
            userEpisode.setNotes(newUserEpisode.getNotes());
            if(newUserEpisode.isWatched() && !userEpisode.isWatched()) {
                userEpisode.setWatchedDate(LocalDate.now());
            }
            userEpisode.setWatched(newUserEpisode.isWatched());
            userEpisode.setRating(newUserEpisode.getRating());
            return userEpisodeRepository.save(userEpisode);
        }
    }
}

