package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.user.AppUser;
import com.duroc.mediatracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/mediatracker/users")
public class UserController {
    @Autowired
    UserService userService;

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
    /**
     * todo:
     *      Spring Security set up for the "user" endpoint
     *      (Potentially) refactor Show and Film endpoint('s) for user specified things:
     *         - move them to the User Controller
     *      Filter for the endpoint that can be applied to Spring Security(?)
     *      Specific endpoint for adding a User to the database (eg: they just signed up)
     *          - + all logic that comes with that (Service, Repo, Tests etc..)
     */


    // This was just a test endpoint for receiving a token from the client and authenticating it
    @PostMapping("/auth")
    public ResponseEntity<String> authenticateUser(HttpServletRequest request, HttpServletResponse response) {
        String idToken = request.getHeader("Authorization");

        // idToken comes from the client app (shown above)
        FirebaseToken decodedToken;

        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        // uid Can be the unique identifier of the user on the database (Appuser's OauthID field possibly (?))
        String uid = decodedToken.getUid();

        //Through the decodedToken -> a few credentials can be obtained eg: the user's name or email
        System.out.println("User name: " + decodedToken.getName());


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
