package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.duroc.mediatracker.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/mediatracker/shows")
public class ShowController {
    @Autowired
    ShowService showService;

    @GetMapping("/search/{searchQuery}")
    public ResponseEntity<ShowSearchResult> getShowSearchResults(@PathVariable String searchQuery) throws IOException, InterruptedException {
        ShowSearchResult searchResults = showService.getShowSearchResults(searchQuery);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}
