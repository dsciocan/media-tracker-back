package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserShow;
import com.duroc.mediatracker.model.user.UserShowId;
import com.duroc.mediatracker.repository.ShowRepository;
import com.duroc.mediatracker.repository.UserShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserShowServiceImplementation implements  UserShowService {

    @Autowired
    UserShowRepository userShowRepository;

    @Autowired
    UserService userService;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    ShowService showService;

    @Autowired
    UserEpisodeService userEpisodeService;

    @Override
    public List<UserShow> getAllShowsFromUserList() {
        AppUser user = userService.getUser();
        return userShowRepository.findByUserShowIdAppUser(user);
    }


    @Override
    public UserShow saveShowToUserList(UserShow userShow, Long showApiId) throws IOException, InterruptedException {
        AppUser user = userService.getUser();
        Show show = showService.saveShowDetails(showApiId);
        UserShowId userShowId = new UserShowId(user, show);
        userShow.setUserShowId(userShowId);
        if(userShow.getStatus().equalsIgnoreCase("Watching")) {
            userShow.setDateStarted(LocalDate.now());
            userShow.setEpisodesWatched(0);
        } else if(userShow.getStatus().equalsIgnoreCase("Watched")) {
            userShow.setDateStarted(LocalDate.now());
            userShow.setDateCompleted(LocalDate.now());
            userShow.setEpisodesWatched(show.getNumberOfEpisodes());
        }
        userShowRepository.save(userShow);
        userEpisodeService.saveAllShowEpisodesAsUserEpisodes(show.getId());
        return userShow;
    }


    @Override
    public UserShow getUserShowByShowId(Long showId) {
            AppUser user = userService.getUser();
            Show show = showService.getSavedShow(showId);
            UserShowId userShowId = new UserShowId(user, show);
            if(userShowRepository.findById(userShowId).isPresent()) {
                return userShowRepository.findById(userShowId).get();
            } else {
             throw new ItemNotFoundException("Could not find TV show with requested id in specified user's list");
            }
    }


    public String genreValidator(String genre) {
        if(genre != null && genre.contains(" ")) {
            return genre.substring(0, genre.indexOf(" "));
        } else {
            return genre;
        }
    }

    @Override
    public List<UserShow> getUserShowsByWatchStatusAndOptionalGenre(String status, String genre) {
        List<UserShow> userShows = getAllShowsFromUserList();
        List<UserShow> filteredList = new ArrayList<>();
        String validatedGenre = genreValidator(genre);
        for(UserShow userShow : userShows) {
            if(userShow.getStatus().equalsIgnoreCase(status)) {
                if(validatedGenre == null || validatedGenre.isEmpty()) {
                    filteredList.add(userShow);
                } else if(userShow.getUserShowId().getShow().getGenres().stream().anyMatch((gen) -> {
                    if(gen.contains(" ")) {
                        return gen.substring(0, gen.indexOf(" ")).equalsIgnoreCase(validatedGenre);
                    } else {
                        return gen.equalsIgnoreCase(validatedGenre);
                    }
                })) {
                    filteredList.add(userShow);
                }
            }
        }
        return filteredList;
    }

    @Override
    public UserShow changeUserShowDetails(Long showId, UserShow newUserShow) {
        UserShow userShow = getUserShowByShowId(showId);
        List<UserEpisode> userEpisodes = userEpisodeService.getUserEpisodeListByShowId(userShow.getUserShowId().getShow().getId());
        if(newUserShow.getUserShowId() == null) {
            newUserShow.setUserShowId(userShow.getUserShowId());
        }
//        if(!Objects.equals(newUserShow.getUserShowId(), userShow.getUserShowId())) {
//            throw new InvalidItemException("User and Show properties are not changeable");
//        } else
            if(newUserShow.getStatus() == null) {
            throw new InvalidItemException("Show must have a status on the user's list");
        } else {
            userShow.setNotes(newUserShow.getNotes());
            userShow.setEpisodesWatched(newUserShow.getEpisodesWatched());
            if(newUserShow.getStatus().equalsIgnoreCase("Watching") && !userShow.getStatus().equalsIgnoreCase("Watching")) {
                userShow.setDateStarted(LocalDate.now());
            }
            if(newUserShow.getStatus().equalsIgnoreCase("Watched") && !userShow.getStatus().equalsIgnoreCase("Watched")) {
                userShow.setDateCompleted(LocalDate.now());
                for(UserEpisode ep : userEpisodes) {
                    ep.setWatched(true);
                    userEpisodeService.changeUserEpisodeDetails(ep.getUserEpisodeId().getEpisode().getId(), ep);
                }
                userShow.setEpisodesWatched(userShow.getUserShowId().getShow().getNumberOfEpisodes());
            }
            if(userShow.getStatus().equalsIgnoreCase("WATCHED") && !newUserShow.getStatus().equalsIgnoreCase("WATCHED")) {
                for(UserEpisode ep : userEpisodes) {
                    ep.setWatched(false);
                    userEpisodeService.changeUserEpisodeDetails(ep.getUserEpisodeId().getEpisode().getId(), ep);
                }
            }
            userShow.setStatus(newUserShow.getStatus());
            userShow.setRating(newUserShow.getRating());
            return userShowRepository.save(userShow);
        }
    }

    @Override
    public HashMap<String, Integer> getNumberOfShowsWatchedByGenre() {
        List<UserShow> allShows = getAllShowsFromUserList();
        HashMap<String, Integer> showsByGenre = new HashMap<>();
        for(UserShow userShow : allShows) {
            if(userShow.getStatus().equalsIgnoreCase("Watched")) {
                userShow.getUserShowId().getShow().getGenres().forEach((genre) ->
                {
                    if(showsByGenre.containsKey(genre)) {
                        showsByGenre.put(genre, showsByGenre.get(genre) + 1);
                    } else {
                        showsByGenre.put(genre, 1);
                    }
                });
            }
        }
        return showsByGenre;
    }

    @Override
    public boolean isUserShowAlreadySaved(Long tmdbId) {
        AppUser user = userService.getUser();
        Show show = showService.getShowByTmdbId(tmdbId);
        UserShowId userShowId = new UserShowId();
        userShowId.setAppUser(user);
        userShowId.setShow(show);

        return userShowRepository.existsById(userShowId);
    }


}
