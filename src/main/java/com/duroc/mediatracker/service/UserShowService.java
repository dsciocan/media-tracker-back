package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserShow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserShowService {
    List<UserShow> getAllShowsFromUserList();

    UserShow saveShowToUserList(UserShow userShow, Long apiShowId) throws IOException, InterruptedException;

    UserShow getUserShowByShowId(Long showId);

    List<UserShow> getUserShowsByWatchStatusAndOptionalGenre(String status, String genre);

    UserShow changeUserShowDetails(Long showId, UserShow newUserShow);

    HashMap<String, Integer> getNumberOfShowsWatchedByGenre();

    boolean isUserShowAlreadySaved(Long tmdbId);
}
