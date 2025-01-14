package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.show_search.ShowSearchResult;

import java.io.IOException;

public interface ShowService {
    ShowSearchResult getShowSearchResults(String query) throws IOException, InterruptedException;
}
