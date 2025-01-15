package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;

import java.io.IOException;

public interface ShowService {
    ShowSearchResult getShowSearchResults(String query) throws IOException, InterruptedException;
    ShowDetails getShowDetails(Long id) throws IOException, InterruptedException;


//    Show getShowDetails2(Long id) throws IOException, InterruptedException;

    Show saveShowDetails(Long id) throws IOException, InterruptedException;
}
