package com.duroc.mediatracker.service;

import com.duroc.mediatracker.Exception.InvalidItemException;
import com.duroc.mediatracker.Exception.ItemNotFoundException;
import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.user.AppUser;
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
    public List<UserShow> getAllShowsFromUserList(Long userId) {
        AppUser user = userService.getUserById(userId);
        return userShowRepository.findByUserShowIdAppUser(user);
    }


    @Override
    public UserShow saveShowToUserList(UserShow userShow, Long userId, Long showApiId) throws IOException, InterruptedException {
        AppUser user = userService.getUserById(userId);
        Show show = showService.saveShowDetails(showApiId);
        UserShowId userShowId = new UserShowId(user, show);
        userShow.setUserShowId(userShowId);
        if(userShow.getStatus().equalsIgnoreCase("Watching")) {
            userShow.setDateStarted(LocalDate.now());
        } else if(userShow.getStatus().equalsIgnoreCase("Watched")) {
            userShow.setDateStarted(LocalDate.now());
            userShow.setDateCompleted(LocalDate.now());
        }
        userEpisodeService.saveAllShowEpisodesAsUserEpisodes(userId, show.getId());
        return userShowRepository.save(userShow);
    }


    @Override
    public UserShow getUserShowByShowId(Long userId, Long showId) {
            AppUser user = userService.getUserById(userId);
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
    public List<UserShow> getUserShowsByWatchStatusAndOptionalGenre(Long userId, String status, String genre) {
        List<UserShow> userShows = getAllShowsFromUserList(userId);
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
    public UserShow changeUserShowDetails(Long userId, Long showId, UserShow newUserShow) {
        UserShow userShow = getUserShowByShowId(userId, showId);
        if(newUserShow.getUserShowId() == null) {
            newUserShow.setUserShowId(userShow.getUserShowId());
        }
        if(!Objects.equals(newUserShow.getUserShowId(), userShow.getUserShowId())) {
            throw new InvalidItemException("User and Show properties are not changeable");
        } else if(newUserShow.getStatus() == null) {
            throw new InvalidItemException("Show must have a status on the user's list");
        } else {
            userShow.setNotes(newUserShow.getNotes());
            if(newUserShow.getStatus().equalsIgnoreCase("Watching") && !userShow.getStatus().equalsIgnoreCase("Watching")) {
                userShow.setDateCompleted(LocalDate.now());
            }
            if(newUserShow.getStatus().equalsIgnoreCase("Watched") && !userShow.getStatus().equalsIgnoreCase("Watched")) {
                userShow.setDateCompleted(LocalDate.now());
            }
            userShow.setStatus(newUserShow.getStatus());
            userShow.setRating(newUserShow.getRating());
            return userShowRepository.save(userShow);
        }
    }

    @Override
    public HashMap<String, Integer> getNumberOfShowsWatchedByGenre(Long userId) {
        List<UserShow> allShows = getAllShowsFromUserList(userId);
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

}
