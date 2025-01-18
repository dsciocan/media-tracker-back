package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.user.UserShow;

import java.io.IOException;
import java.util.List;

public interface UserShowService {
    List<UserShow> getAllShowsFromUserList(Long userId);

    UserShow saveShowToUserList(UserShow userShow, Long userId, Long apiShowId) throws IOException, InterruptedException;

    UserShow getUserShowByShowId(Long userId, Long showId);

    List<UserShow> getUserShowsByWatchStatusAndOptionalGenre(Long userId, String status, String genre);

    UserShow changeUserShowDetails(Long userId, Long showId, UserShow newUserShow);
}
