package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.info.Show;
import com.duroc.mediatracker.model.show_detail.ShowDetails;
import com.duroc.mediatracker.model.show_search.Result;

import java.io.IOException;
import java.util.List;

public interface ShowService {
    List<Result> getShowSearchResults(String query) throws IOException, InterruptedException;
    ShowDetails getShowDetails(Long apiId) throws IOException, InterruptedException;


//    Show getShowDetails2(Long id) throws IOException, InterruptedException;

//    Show saveShowAsUser(Show show);

    Show saveShowDetails(Long apiId) throws IOException, InterruptedException;
    Show getSavedShow(Long id);
    String deleteShowFromDb(Long id);
}
