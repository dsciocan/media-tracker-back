package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.model.user.UserEpisode;
import com.duroc.mediatracker.model.user.UserShow;
import com.duroc.mediatracker.service.UserEpisodeService;
import com.duroc.mediatracker.service.UserService;
import com.duroc.mediatracker.service.UserShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mediatracker/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserShowService userShowService;

    @Autowired
    UserEpisodeService userEpisodeService;

    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        return new ResponseEntity<>(userService.saveUser(appUser), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<AppUser> changeUsername(@PathVariable Long userId, @RequestBody String newUsername) {
        return new ResponseEntity<>(userService.changeUsername(userId, newUsername), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/shows/")
    public ResponseEntity<List<UserShow>> getShowsFromUserList(@PathVariable Long userId) throws IOException, InterruptedException {
        return new ResponseEntity<>(userShowService.getAllShowsFromUserList(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/shows/save/{apiShowId}")
    public ResponseEntity<UserShow> saveShowToUserList(@RequestBody UserShow userShow,
                                                       @PathVariable Long userId, @PathVariable Long apiShowId) throws IOException, InterruptedException {
        return new ResponseEntity<>(userShowService.saveShowToUserList(userShow, userId, apiShowId), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/shows/{showId}")
    public ResponseEntity<UserShow> getUserShowByShowId(@PathVariable Long userId, @PathVariable Long showId) {
        return new ResponseEntity<>(userShowService.getUserShowByShowId(userId, showId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/shows")
    public ResponseEntity<List<UserShow>> getUserShowsByWatchStatusAndOptionalGenre(@PathVariable Long userId,
                                                                                    @RequestParam String status, @RequestParam(required = false) String genre) {
        return new ResponseEntity<>(userShowService.getUserShowsByWatchStatusAndOptionalGenre(userId, status, genre), HttpStatus.OK);
    }

    @PutMapping("/{userId}/shows/{showId}")
    public ResponseEntity<UserShow> changeUserShowDetails(@PathVariable Long userId, @PathVariable Long showId,
                                                          @RequestBody UserShow newUserShow) {
        return new ResponseEntity<>(userShowService.changeUserShowDetails(userId, showId, newUserShow), HttpStatus.OK);
    }


    @GetMapping("/{userId}/shows/{showId}/episodes")
    public ResponseEntity<List<UserEpisode>> getUserEpisodeListByShowId(@PathVariable Long userId, @PathVariable Long showId) {
        return new ResponseEntity<>(userEpisodeService.getUserEpisodeListByShowId(userId, showId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/episode/{episodeId}")
    public ResponseEntity<UserEpisode> getUserEpisodeByEpisodeId(@PathVariable Long userId, @PathVariable Long episodeId) {
        return new ResponseEntity<>(userEpisodeService.getUserEpisodeByEpisodeId(userId, episodeId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/episode/{episodeId}")
    public ResponseEntity<UserEpisode> changeUserEpisodeDetails(@PathVariable Long userId, @PathVariable Long episodeId,
                                                                @RequestBody UserEpisode newUserEpisode) {
        return new ResponseEntity<>(userEpisodeService.changeUserEpisodeDetails(userId, episodeId, newUserEpisode), HttpStatus.OK);
    }
}
