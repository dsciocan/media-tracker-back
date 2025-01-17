package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.Result;
import com.duroc.mediatracker.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mediatracker/shows")
public class ShowController {
    @Autowired
    ShowService showService;

    @GetMapping("/search/{searchQuery}")
    public ResponseEntity<List<Result>> getShowSearchResults(@PathVariable String searchQuery) throws IOException, InterruptedException {
        List<Result> searchResults = showService.getShowSearchResults(searchQuery);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ShowDetails> getShowDetails(@PathVariable Long id) throws IOException, InterruptedException {
        ShowDetails showDetails = showService.getShowDetails(id);
        return new ResponseEntity<>(showDetails, HttpStatus.OK);
    }

//    @GetMapping("/details2/{id}")
//    public ResponseEntity<Show> getShowDetails2(@PathVariable Long id) throws IOException, InterruptedException {
//        Show showDetails = showService.getShowDetails2(id);
//        return new ResponseEntity<>(showDetails, HttpStatus.OK);
//    }

    @PostMapping("/save")
    public ResponseEntity<Show> saveShowDetails(@RequestBody Long id) throws IOException, InterruptedException {
        Show savedShow = showService.saveShowDetails(id);
        return new ResponseEntity<>(savedShow, HttpStatus.OK);
    }

    @GetMapping("/saved/{id}")
    public ResponseEntity<Show> getSavedShowById(@PathVariable Long id) {
        return new ResponseEntity<>(showService.getSavedShow(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        return new ResponseEntity<>(showService.deleteShowFromDb(id), HttpStatus.OK);
    }
}
