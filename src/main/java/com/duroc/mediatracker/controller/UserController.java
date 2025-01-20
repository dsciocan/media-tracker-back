package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.*;
import com.duroc.mediatracker.service.UserEpisodeService;
import com.duroc.mediatracker.service.UserFilmService;
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

    @Autowired
    UserFilmService userFilmService;

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

    // UserFilm methods
    @PostMapping("/{userId}/{movieId}")
    public ResponseEntity<UserFilm> saveUserFilm(@PathVariable Long userId, @PathVariable Long movieId, @RequestBody UserFilm userFilm) throws IOException, InterruptedException {
        UserFilm savedUserFilm = userFilmService.saveUserFilm(userFilm, userId, movieId);
        return new ResponseEntity<>(savedUserFilm, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserFilm>> getUserFilms(@PathVariable Long userId) {
        List<UserFilm> userFilms = userFilmService.getAllUserFilms(userId);
        return new ResponseEntity<>(userFilms,HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{filmDbId}")
    public ResponseEntity<UserFilm> updateUserFilm(@PathVariable Long userId, @PathVariable Long filmDbId, @RequestBody UserFilm updatedUserFilm) {
        UserFilm userFilm = userFilmService.updateUserFilm(updatedUserFilm, userId, filmDbId);
        return new ResponseEntity<>(userFilm, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{filmDbId}")
    public ResponseEntity<UserFilm> getUserFilmById(@PathVariable Long userId, @PathVariable Long filmDbId) {
        UserFilm userFilm = userFilmService.getUserFilmById(userId, filmDbId);
        return new ResponseEntity<>(userFilm, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{filmDbId}")
    public ResponseEntity<String> deleteUserFilmById(@PathVariable Long userId, @PathVariable Long filmDbId) {
        userFilmService.deleteUserFilmById(userId, filmDbId);
        return new ResponseEntity<>("UserFilm successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/{userId}/search")
    public ResponseEntity<?> getUserFilmsByStatus(@PathVariable Long userId, @RequestParam Status status) {
        List<UserFilm> userFilms = userFilmService.getUserFilmsByStatus(userId, status);
        if(userFilms.isEmpty()) {
            return new ResponseEntity<>("No films found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userFilms, HttpStatus.OK);
    }
}
