package com.duroc.mediatracker.service;

import com.duroc.mediatracker.model.dao.ShowDAO;
import com.duroc.mediatracker.model.show_search.Result;
import com.duroc.mediatracker.model.show_search.ShowSearchResult;
import com.duroc.mediatracker.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ShowServiceImplementation implements ShowService {

    @Autowired
    ShowRepository showRepository;


    @Override
    public ShowSearchResult getShowSearchResults(String query) throws IOException, InterruptedException {
        ShowSearchResult showSearchResult = ShowDAO.requestShowSearchData(query);
        for(Result result : showSearchResult.results()) {
            String url = "https://image.tmdb.org/t/p/original" + result.getPoster_path();
            result.setPoster_path(url);
        }
        return showSearchResult;
    }
}
