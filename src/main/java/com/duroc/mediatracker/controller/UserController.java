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
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;

// Token is sent from the backend regardless of signing up with us or already being a user
// some sort of check logic is needed to see if they need to be a user on our db
// Potentially (Just have a method to check to see if the user is already on the local db)
    // Have some sort of entry endpoint for when a user opens the app
    // (Have a token sent from the front to the back, and we have a verify endpoint to see if the user is already on the db)
    // Or the first endpoint that is most likely to be called will have that check
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

    /**
     * todo:
     *      S̶p̶r̶i̶n̶g̶ S̶e̶c̶u̶r̶i̶t̶y̶ s̶e̶t̶ u̶p̶ f̶o̶r̶ t̶h̶e̶ "u̶s̶e̶r̶" e̶n̶d̶p̶o̶i̶n̶t̶
     *      (Potentially) refactor Show and Film endpoint('s) for user specified things:
     *         - move them to the User Controller
     *      F̶i̶l̶t̶e̶r̶ f̶o̶r̶ t̶h̶e̶ e̶n̶d̶p̶o̶i̶n̶t̶ t̶h̶a̶t̶ c̶a̶n̶ b̶e̶ a̶p̶p̶l̶i̶e̶d̶ t̶o̶ S̶p̶r̶i̶n̶g̶ S̶e̶c̶u̶r̶i̶t̶y̶
     *      Specific endpoint for adding a User to the database (eg: they just signed up) -> something like authenticateUserIsInDatabase
     *          - + all logic that comes with that (Service, Repo, Tests etc..)
     */


    // Ths is an example of how an endpoint can look like for receiving a token from the client and checking the user in the db

    @GetMapping("/auth")
    public ResponseEntity<AppUser> authenticateUserIsInDatabase() {
        // ... call whatever userService method needed
        AppUser appUser = userService.getUser();
        // HttpStatus.OK only needs to be sent in this case because if anything goes wrong then an Exception should be thrown and
        // the Global exception handler should deal with it
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }


//    @GetMapping("/{userId}")
//    public ResponseEntity<AppUser> getUserById(@PathVariable Long userId) {
//        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
//    }

//    @PostMapping("/save")
//    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
//        return new ResponseEntity<>(userService.saveUser(appUser), HttpStatus.OK);
//    }

//    @PatchMapping("/{userId}")
//    public ResponseEntity<AppUser> changeUsername(@PathVariable Long userId, @RequestBody String newUsername) {
//        return new ResponseEntity<>(userService.changeUsername(userId, newUsername), HttpStatus.OK);
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        return new ResponseEntity<>(userService.deleteUser(), HttpStatus.OK);
    }

    @GetMapping("/shows/")
    public ResponseEntity<List<UserShow>> getShowsFromUserList() throws IOException, InterruptedException {
        return new ResponseEntity<>(userShowService.getAllShowsFromUserList(), HttpStatus.OK);
    }

    @PostMapping("/shows/save/{apiShowId}")
    public ResponseEntity<UserShow> saveShowToUserList(@RequestBody UserShow userShow,
                                                       @PathVariable Long apiShowId) throws IOException, InterruptedException {
        return new ResponseEntity<>(userShowService.saveShowToUserList(userShow, apiShowId), HttpStatus.CREATED);
    }

    @GetMapping("/shows/{showId}")
    public ResponseEntity<UserShow> getUserShowByShowId(@PathVariable Long showId) {
        return new ResponseEntity<>(userShowService.getUserShowByShowId(showId), HttpStatus.OK);
    }

    @GetMapping("/shows")
    public ResponseEntity<List<UserShow>> getUserShowsByWatchStatusAndOptionalGenre(@RequestParam String status, @RequestParam(required = false) String genre) {
        return new ResponseEntity<>(userShowService.getUserShowsByWatchStatusAndOptionalGenre(status, genre), HttpStatus.OK);
    }

    @PutMapping("/shows/{showId}")
    public ResponseEntity<UserShow> changeUserShowDetails(@PathVariable Long showId,
                                                          @RequestBody UserShow newUserShow) {
        return new ResponseEntity<>(userShowService.changeUserShowDetails(showId, newUserShow), HttpStatus.OK);
    }


    @GetMapping("/shows/{showId}/episodes")
    public ResponseEntity<List<UserEpisode>> getUserEpisodeListByShowId(@PathVariable Long showId) {
        return new ResponseEntity<>(userEpisodeService.getUserEpisodeListByShowId(showId), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}")
    public ResponseEntity<UserEpisode> getUserEpisodeByEpisodeId(@PathVariable Long episodeId) {
        return new ResponseEntity<>(userEpisodeService.getUserEpisodeByEpisodeId(episodeId), HttpStatus.OK);
    }

    @PutMapping("/episode/{episodeId}")
    public ResponseEntity<UserEpisode> changeUserEpisodeDetails(@PathVariable Long episodeId,
                                                                @RequestBody UserEpisode newUserEpisode) {
        return new ResponseEntity<>(userEpisodeService.changeUserEpisodeDetails(episodeId, newUserEpisode), HttpStatus.OK);
    }

    // UserFilm methods
    @PostMapping("/films/{movieId}")
    public ResponseEntity<UserFilm> saveUserFilm(@PathVariable Long movieId, @RequestBody UserFilm userFilm) throws IOException, InterruptedException {
        UserFilm savedUserFilm = userFilmService.saveUserFilm(userFilm, movieId);
        return new ResponseEntity<>(savedUserFilm, HttpStatus.CREATED);
    }

    @GetMapping("/films")
    public ResponseEntity<List<UserFilm>> getUserFilms() {
        List<UserFilm> userFilms = userFilmService.getAllUserFilms();
        return new ResponseEntity<>(userFilms,HttpStatus.OK);
    }

    @PatchMapping("/films/{filmDbId}")
    public ResponseEntity<UserFilm> updateUserFilm(@PathVariable Long filmDbId, @RequestBody UserFilm updatedUserFilm) {
        UserFilm userFilm = userFilmService.updateUserFilm(updatedUserFilm, filmDbId);
        return new ResponseEntity<>(userFilm, HttpStatus.OK);
    }

    @GetMapping("/films/{filmDbId}")
    public ResponseEntity<UserFilm> getUserFilmById(@PathVariable Long filmDbId) {
        UserFilm userFilm = userFilmService.getUserFilmById(filmDbId);
        return new ResponseEntity<>(userFilm, HttpStatus.OK);
    }

    @DeleteMapping("/films/{filmDbId}")
    public ResponseEntity<String> deleteUserFilmById(@PathVariable Long filmDbId) {
        userFilmService.deleteUserFilmById(filmDbId);
        return new ResponseEntity<>("UserFilm successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/films/search")
    public ResponseEntity<?> getUserFilmsByStatus(@RequestParam Status status) {
        List<UserFilm> userFilms = userFilmService.getUserFilmsByStatus(status);
        if(userFilms.isEmpty()) {
            return new ResponseEntity<>("No films found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userFilms, HttpStatus.OK);
    }

    @GetMapping("/totalWatchedRuntime")
    public ResponseEntity<Integer> getTotalWatchedRuntime() {
        Integer totalRuntime = userService.totalRuntime();

        return new ResponseEntity<>(totalRuntime, HttpStatus.OK);
    }

    @GetMapping("/genreStats")
    public ResponseEntity<Map<String,Integer>> getGenreStats() {
        return new ResponseEntity<>(userService.getAllByGenre(),HttpStatus.OK);
    }
}
