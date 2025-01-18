package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        userService.getUser();
        // HttpStatus.OK only needs to be sent in this case because if anything goes wrong then an Exception should be thrown and
        // the Global exception handler should deal with it
        return new ResponseEntity<>(HttpStatus.OK);
    }


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

}
