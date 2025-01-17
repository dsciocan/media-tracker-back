package com.duroc.mediatracker.service;

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
import java.util.List;

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

    @Override
    public List<UserShow> getAllShowsFromUserList(Long userId) {
        AppUser user = userService.getUserById(userId);
        return userShowRepository.findByUserShowIdAppUser(user);
    }


    @Override
    public UserShow saveShowToUserList(UserShow userShow, Long userId, Long showApiId) throws IOException, InterruptedException {
        AppUser user = userService.getUserById(userId);
        Show show = showService.saveShowDetails(showApiId);
        userShow.getUserShowId().setAppUser(user);
        userShow.getUserShowId().setShow(show);
        if(userShow.getStatus().equals("Watching")) {
            userShow.setDateStarted(LocalDate.now());
        } else if(userShow.getStatus().equals("Watched")) {
            userShow.setDateStarted(LocalDate.now());
            userShow.setDateCompleted(LocalDate.now());
        }
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


}
